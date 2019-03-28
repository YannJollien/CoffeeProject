package com.example.coffeeproject2.ui.storage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.StorageViewModel;
import com.example.coffeeproject2.adapter.StorageAdapter;
import com.example.coffeeproject2.database.entity.Storage;

import java.util.List;

public class StorageViewList extends AppCompatActivity {
    public final static int ADD_NOTE_REQUEST = 1;
    public final static int EDIT_NOTE_REQUEST = 2;

    private StorageViewModel storageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_view_1);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_storage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StorageAdapter adapter = new StorageAdapter();
        recyclerView.setAdapter(adapter);

        //set Titel of View
        setTitle("Edit Coffees");


        //Views
        storageViewModel = ViewModelProviders.of(this).get(StorageViewModel.class);
        storageViewModel.getAllStorage().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(@Nullable List<Storage> storages) {
                adapter.setStorage(storages);
            }
        });

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.storage_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        /**adapter.setOnItemClickListener(new StorageAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Storage storage) {
        Intent intent = new Intent(StorageViewActivity.this, StorageEditActivity.class);
        intent.putExtra(StorageEditActivity.EXTRA_ID, storage.getId());

        }
        });

         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                storageViewModel.delete(adapter.getStorageAt(viewHolder.getAdapterPosition()));
                //startActivity(new Intent(StorageViewActivity.this, StorageEditActivity.class));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new StorageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Storage storage) {
                Intent intent = new Intent(StorageViewList.this, StorageEditActivity.class);
                intent.putExtra(StorageEditActivity.EXTRA_ID, storage.getId());
                intent.putExtra(StorageEditActivity.EXTRA_TYPE, storage.getType());
                intent.putExtra(StorageEditActivity.EXTRA_AMOUNT, storage.getAmount() + "");
                intent.putExtra(StorageEditActivity.EXTRA_DATE, storage.getDate());
                startActivityForResult(intent, EDIT_NOTE_REQUEST    );
            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String date = data.getStringExtra(StorageEditActivity.EXTRA_DATE);
            String spinner = data.getStringExtra(StorageEditActivity.EXTRA_TYPE);

            Storage storage = new Storage(spinner,Double.parseDouble(amount) , date);
            storageViewModel.insert(storage);
        }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(StorageEditActivity.EXTRA_ID, -1 );

            if(id == -1){
                Toast.makeText(this, "Storage updated", Toast.LENGTH_SHORT).show();
                return;
            }

            // String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String date = data.getStringExtra(StorageEditActivity.EXTRA_DATE);
            String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String spinner = data.getStringExtra(StorageEditActivity.EXTRA_TYPE);

            //double amount2 = Double.parseDouble(amount);

            Storage storage = new Storage(spinner,Double.parseDouble(amount) , date);
            storage.setId(id);
            storageViewModel.update(storage);
        }
    }

    //set the add item in Actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When add icon clicked open add Actitivy
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                //Show scanner
                startActivity(new Intent(getApplicationContext(), StorageAddActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
