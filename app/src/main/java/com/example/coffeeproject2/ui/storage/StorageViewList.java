package com.example.coffeeproject2.ui.storage;


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

import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.adapter.StorageAdapter;
import com.example.coffeeproject2.adapter.StorageAdapterView;
import com.example.coffeeproject2.database.entity.Storage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.coffeeproject2.ui.storage.StorageEditActivity.EXTRA_ID;

public class StorageViewList extends AppCompatActivity {
    public final static int ADD_NOTE_REQUEST = 1;
    public final static int EDIT_NOTE_REQUEST = 2;

    DatabaseReference reference;

    ArrayList<Storage> storageList;
    StorageAdapterView adapter;

    DatabaseReference databaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_view_1);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_storage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final StorageAdapter storageAdapter = new StorageAdapter();
        recyclerView.setAdapter(storageAdapter);

        storageList = new ArrayList<>();

        databaseStorage = FirebaseDatabase.getInstance().getReference("storage");

        reference = FirebaseDatabase.getInstance().getReference().child("storage");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Storage storage = dataSnapshot1.getValue(Storage.class);
                    storageList.add(storage);
                    for (int i = 0; i < storageList.size();i++){
                        System.out.println(storageList.get(i).getId());
                    }
                }
                adapter = new StorageAdapterView(getApplicationContext(), storageList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.storage_toolbar);
        setSupportActionBar(myChildToolbar);
        //myChildToolbar.setTitleTextColor(0xFFFFFFFF);
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

                System.out.println();
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StorageViewList.this, StorageViewActivity.class));

            }
        }).attachToRecyclerView(recyclerView);

        storageAdapter.setOnItemClickListener(new StorageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Storage storage) {
                Intent intent = new Intent(StorageViewList.this, StorageEditActivity.class);
                intent.putExtra(EXTRA_ID, storage.getId());
                intent.putExtra(StorageEditActivity.EXTRA_TYPE, storage.getType());
                intent.putExtra(StorageEditActivity.EXTRA_AMOUNT, storage.getAmount() + "");
                intent.putExtra(StorageEditActivity.EXTRA_DATE, storage.getDate());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

    }

    public void deleteStorage(String id){
        DatabaseReference deleteStorage = FirebaseDatabase.getInstance().getReference("storage").child(id);
        deleteStorage.removeValue();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String date = data.getStringExtra(StorageEditActivity.EXTRA_DATE);
            String spinner = data.getStringExtra(StorageEditActivity.EXTRA_TYPE);

            Storage storage = new Storage(spinner, Double.parseDouble(amount), date);
            //storageViewModel.insert(recyclerView);
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Storage updated", Toast.LENGTH_SHORT).show();
                return;
            }

            // String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String date = data.getStringExtra(StorageEditActivity.EXTRA_DATE);
            String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String spinner = data.getStringExtra(StorageEditActivity.EXTRA_TYPE);

            //double amount2 = Double.parseDouble(amount);

            Storage storage = new Storage(spinner, Double.parseDouble(amount), date);
            //storageViewModel.update(recyclerView);
        }
    }

    /*public final static int ADD_NOTE_REQUEST = 1;
    public final static int EDIT_NOTE_REQUEST = 2;


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



        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.storage_toolbar);
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
                //storageViewModel.delete(adapter.getStorageAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StorageViewList.this, StorageViewActivity.class));

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
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String date = data.getStringExtra(StorageEditActivity.EXTRA_DATE);
            String spinner = data.getStringExtra(StorageEditActivity.EXTRA_TYPE);

            Storage storage = new Storage(spinner, Double.parseDouble(amount), date);
            //storageViewModel.insert(recyclerView);
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(StorageEditActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Storage updated", Toast.LENGTH_SHORT).show();
                return;
            }

            // String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String date = data.getStringExtra(StorageEditActivity.EXTRA_DATE);
            String amount = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String spinner = data.getStringExtra(StorageEditActivity.EXTRA_TYPE);

            //double amount2 = Double.parseDouble(amount);

            Storage storage = new Storage(spinner, Double.parseDouble(amount), date);
            //storageViewModel.update(recyclerView);
        }
    }*/
}
