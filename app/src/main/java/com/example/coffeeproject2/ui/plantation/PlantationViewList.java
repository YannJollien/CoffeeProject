package com.example.coffeeproject2.ui.plantation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.adapter.PlantationAdapter;
import com.example.coffeeproject2.adapter.PlantationAdapterView;
import com.example.coffeeproject2.database.entity.Plantation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.coffeeproject2.ui.plantation.PlantationEditActivity.EXTRA_ID;

import java.util.ArrayList;

public class PlantationViewList extends AppCompatActivity {
    public final static int ADD_NOTE_REQUEST = 1;
    public final static int EDIT_NOTE_REQUEST = 2;

    DatabaseReference reference;

    ArrayList<Plantation> plantationList;
    PlantationAdapterView adapter;

    DatabaseReference databaseStorage;
    PlantationAdapter plantationAdapter = new PlantationAdapter();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_view_2);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_plantation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        PlantationAdapter plantationAdapter = new PlantationAdapter();
        recyclerView.setAdapter(plantationAdapter);

        plantationList = new ArrayList<>();

        databaseStorage = FirebaseDatabase.getInstance().getReference("plantation");

        reference = FirebaseDatabase.getInstance().getReference().child("plantation");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Plantation plantation = dataSnapshot1.getValue(Plantation.class);
                    plantationList.add(plantation);
                    for (int i = 0; i < plantationList.size(); i++){
                        System.out.println(plantationList.get(i).getId());
                    }
                }
                adapter = new PlantationAdapterView(getApplicationContext(), plantationList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.plantation_toolbar);
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
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PlantationViewList.this, PlantationViewActivity.class));

            }
        }).attachToRecyclerView(recyclerView);

        plantationAdapter.setOnItemClickListener(new PlantationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plantation plantation) {
                System.out.println("CLick");
                Intent intent = new Intent(PlantationViewList.this, PlantationEditActivity.class);
                /*intent.putExtra(StorageEditActivity.EXTRA_ID, storage.getId());
                intent.putExtra(StorageEditActivity.EXTRA_TYPE, storage.getType());
                intent.putExtra(StorageEditActivity.EXTRA_AMOUNT, storage.getHectare() + "");
                intent.putExtra(StorageEditActivity.EXTRA_DATE, storage.getDate());*/
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });


    }

    public void deleteStorage(String id){

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String amount = data.getStringExtra(PlantationEditActivity.EXTRA_HECTARE);
            String date = data.getStringExtra(PlantationEditActivity.EXTRA_DATE);
            String spinner = data.getStringExtra(PlantationEditActivity.EXTRA_TYPE);

            Plantation plantation = new Plantation(spinner, Double.parseDouble(amount), date);
            //storageViewModel.insert(recyclerView);
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Storage updated", Toast.LENGTH_SHORT).show();
                return;
            }

            // String hectare = data.getStringExtra(StorageEditActivity.EXTRA_AMOUNT);
            String date = data.getStringExtra(PlantationEditActivity.EXTRA_DATE);
            String amount = data.getStringExtra(PlantationEditActivity.EXTRA_HECTARE);
            String spinner = data.getStringExtra(PlantationEditActivity.EXTRA_TYPE);

            //double amount2 = Double.parseDouble(hectare);

            Plantation plantation = new Plantation(spinner, Double.parseDouble(amount), date);
            //storageViewModel.update(recyclerView);
        }
    }
}
