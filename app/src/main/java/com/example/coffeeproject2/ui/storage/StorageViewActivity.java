package com.example.coffeeproject2.ui.storage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.coffeeproject2.adapter.StorageAdapterView;
import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.ui.plantation.PlantationActivity;
import com.example.coffeeproject2.ui.plantation.PlantationAddActivity;

import java.util.List;

public class StorageViewActivity extends AppCompatActivity {
    public final static int ADD_NOTE_REQUEST = 1;
    public final static int EDIT_NOTE_REQUEST = 2;

    Button bEdit;
    Button bAdd;

    private StorageViewModel storageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_view);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_storage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StorageAdapterView adapter = new StorageAdapterView();
        recyclerView.setAdapter(adapter);

        bEdit = (Button)findViewById(R.id.btn_edit);
        bAdd = (Button)findViewById(R.id.btn_add);

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StorageViewActivity.this,  StorageViewList.class));
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StorageViewActivity.this,  StorageAddActivity.class));
            }
        });
        //set Titel of View
        setTitle("Coffee");


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
        //myChildToolbar.setTitleTextColor(0xFFFFFFFF);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


    }


}
