package com.example.coffeeproject2.ui.storage;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.StorageViewModel;
import com.example.coffeeproject2.adapter.StorageAdapter;
import com.example.coffeeproject2.database.entity.Storage;

import java.util.List;

public class StorageAllFragment extends Fragment {

    private StorageViewModel storageViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storage_view_all,viewGroup,false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_storage);

        final StorageAdapter storageAdapter = new StorageAdapter();
        recyclerView.setAdapter(storageAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        //Views
        storageViewModel = ViewModelProviders.of(this).get(StorageViewModel.class);
        storageViewModel.getAllStorage().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(@Nullable List<Storage> storages) {
                storageAdapter.setStorage(storages);
                for (int i = 0; i  < storages.size(); i++) {
                    System.out.println(storages.get(i));
                }
            }
        });



        return view;

    }
}