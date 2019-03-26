package com.example.coffeeproject2.ui.storage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.StorageViewModel;
import com.example.coffeeproject2.adapter.StorageAdapter;
import com.example.coffeeproject2.database.entity.Storage;

import java.util.List;

public class StorageViewActivity extends AppCompatActivity {

    private StorageViewModel storageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_view);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_storage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StorageAdapter adapter = new StorageAdapter();
        recyclerView.setAdapter(adapter);

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

        adapter.setOnItemClickListener(new StorageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Storage storage) {
                Intent intent = new Intent(StorageViewActivity.this, StorageAddEditActivity.class);

            }
        });

    }

}
