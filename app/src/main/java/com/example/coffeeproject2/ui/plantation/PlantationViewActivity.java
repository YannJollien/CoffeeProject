package com.example.coffeeproject2.ui.plantation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.adapter.PlantationAdapterView;
import com.example.coffeeproject2.adapter.StorageAdapterView;
import com.example.coffeeproject2.database.entity.Plantation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlantationViewActivity extends AppCompatActivity {

    DatabaseReference reference;

    Button bEdit;
    Button bAdd;

    ArrayList<Plantation> plantationList;
    PlantationAdapterView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_view);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_plantation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        plantationList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("plantation");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Plantation plantation = dataSnapshot1.getValue(Plantation.class);
                    plantationList.add(plantation);
                }
                adapter = new PlantationAdapterView(getApplicationContext(), plantationList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bEdit = (Button) findViewById(R.id.btn_edit_plantation);
        bAdd = (Button) findViewById(R.id.btn_add_plantation);

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlantationViewActivity.this, PlantationViewList.class));
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlantationViewActivity.this, PlantationAddActivity.class));
            }
        });
        //set Titel of View
        setTitle("Plantation");


        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.plantation_toolbar);
        setSupportActionBar(myChildToolbar);
        //myChildToolbar.setTitleTextColor(0xFFFFFFFF);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }

}
