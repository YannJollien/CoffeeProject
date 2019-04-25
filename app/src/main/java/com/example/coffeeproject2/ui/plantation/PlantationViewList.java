package com.example.coffeeproject2.ui.plantation;

import android.arch.lifecycle.ViewModelProviders;
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
import com.example.coffeeproject2.ui.storage.StorageViewActivity;
import com.example.coffeeproject2.ui.storage.StorageViewList;
import com.example.coffeeproject2.util.OnAsyncEventListener;
import com.example.coffeeproject2.viewmodel.plantation.PlantationListViewModel;
import com.example.coffeeproject2.viewmodel.storage.StorageListViewModel;
import com.google.firebase.auth.FirebaseAuth;
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

    PlantationListViewModel model;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_view_2);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view_plantation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final PlantationAdapter plantationAdapter = new PlantationAdapter();
        recyclerView.setAdapter(plantationAdapter);

        plantationList = new ArrayList<>();

        databaseStorage = FirebaseDatabase.getInstance().getReference("plantation");

        reference = FirebaseDatabase.getInstance().getReference().child("plantation");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    System.out.println("Datasnapshot1 get Key----->:" + dataSnapshot1.getKey());
                    System.out.println("Datasnapshot1 get Key----->:" + dataSnapshot1.getValue());
                    Plantation plantation = dataSnapshot1.getValue(Plantation.class);
                    plantation.setId(dataSnapshot1.getKey());
                    System.out.println("ID von Plantation nach snapshot:" + plantation);
                    plantationList.add(plantation);
                    for (int i = 0; i < plantationList.size(); i++){
                        System.out.println("ID von ViewList Plantation: " + plantationList.get(i).getId());
                    }
                }
                plantationAdapter.setPlantation(plantationList);
                adapter = new PlantationAdapterView(getApplicationContext(), plantationList);
                recyclerView.setAdapter(plantationAdapter);

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
                int position = viewHolder.getAdapterPosition();
                System.out.println("--------------------------" + position);

                Plantation plantation = adapter.getPlantation(position);

                System.out.println("----------------------id-----" + plantation.getId());
                reference.child(plantation.getId()).removeValue();
                startActivity(new Intent(PlantationViewList.this, PlantationViewActivity.class));
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        plantationAdapter.setOnItemClickListener(new PlantationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plantation plantation) {
                System.out.println("Click");
                //startActivity(new Intent(StorageViewList.this, StorageEditActivity.class));

                Intent intent = new Intent(PlantationViewList.this, PlantationEditActivity.class);
                intent.putExtra(PlantationEditActivity.EXTRA_ID, plantation.getId());
                System.out.println(plantation.getType());
                System.out.println(plantation.getId());
                System.out.println(plantation.getHectare());
                System.out.println(plantation.getDate());
                intent.putExtra(PlantationEditActivity.EXTRA_TYPE, plantation.getType());
                intent.putExtra(PlantationEditActivity.EXTRA_HECTARE, plantation.getHectare() + "");
                intent.putExtra(PlantationEditActivity.EXTRA_DATE, plantation.getDate());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });


    }

    public void deletePlantation(Plantation plantation){
        PlantationListViewModel.Factory factory = new PlantationListViewModel.Factory(
                getApplication(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        model = ViewModelProviders.of(this, factory).get(PlantationListViewModel.class);
        model.deletePlantation(plantation, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        startActivity(new Intent(PlantationViewList.this, PlantationViewActivity.class));
        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
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
