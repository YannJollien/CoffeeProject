package com.example.coffeeproject2.ui.plantation;

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
import android.widget.Toast;

import com.example.coffeeproject2.PlantationViewModel;
import com.example.coffeeproject2.R;
import com.example.coffeeproject2.adapter.PlantationAdapter;
import com.example.coffeeproject2.database.entity.Plantation;
import com.example.coffeeproject2.ui.storage.StorageAddActivity;

import java.util.List;

public class PlantationViewList extends AppCompatActivity {
    public final static int ADD_NOTE_REQUEST = 1;
    public final static int EDIT_NOTE_REQUEST = 2;

    private PlantationViewModel plantationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_view_2);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_plantation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PlantationAdapter adapter = new PlantationAdapter();
        recyclerView.setAdapter(adapter);

        //Views
        plantationViewModel = ViewModelProviders.of(this).get(PlantationViewModel.class);
        plantationViewModel.getAllPlantation().observe(this, new Observer<List<Plantation>>() {
            @Override
            public void onChanged(@Nullable List<Plantation> plantations) {
                adapter.setPlantation(plantations);
            }
        });

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.plantation_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                plantationViewModel.delete(adapter.getStorageAt(viewHolder.getAdapterPosition()));
                //startActivity(new Intent(StorageViewActivity.this, StorageEditActivity.class));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new PlantationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plantation plantation) {
                Intent intent = new Intent(PlantationViewList.this, PlantationEditActivity.class);
                intent.putExtra(PlantationEditActivity.EXTRA_ID, plantation.getId());
                intent.putExtra(PlantationEditActivity.EXTRA_TYPE, plantation.getType());
                intent.putExtra(PlantationEditActivity.EXTRA_HECTARE, plantation.getHectare() + "");
                intent.putExtra(PlantationEditActivity.EXTRA_DATE, plantation.getDate());
                startActivityForResult(intent, EDIT_NOTE_REQUEST    );
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String hectare = data.getStringExtra(PlantationEditActivity.EXTRA_HECTARE);
            String date = data.getStringExtra(PlantationEditActivity.EXTRA_DATE);
            String spinner = data.getStringExtra(PlantationEditActivity.EXTRA_TYPE);

            Plantation plantation = new Plantation(spinner,Double.parseDouble(hectare) , date);
            plantationViewModel.insert(plantation);
        }else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(PlantationEditActivity.EXTRA_ID, -1 );

            if(id == -1){
                Toast.makeText(this, "Plantation updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String date = data.getStringExtra(PlantationEditActivity.EXTRA_DATE);
            String hectare = data.getStringExtra(PlantationEditActivity.EXTRA_HECTARE);
            String spinner = data.getStringExtra(PlantationEditActivity.EXTRA_TYPE);

            Plantation plantation = new Plantation(spinner,Double.parseDouble(hectare) , date);
            plantation.setId(id);
            plantationViewModel.update(plantation);
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
                startActivity(new Intent(getApplicationContext(), PlantationAddActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
