package com.example.coffeeproject2.ui.storage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.StorageViewModel;

public class StorageAllFragment extends Fragment {

    private StorageViewModel storageViewModel;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.storage_view_all, viewGroup, false);
    }
}