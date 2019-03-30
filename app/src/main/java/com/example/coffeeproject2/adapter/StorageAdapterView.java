package com.example.coffeeproject2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.AdapterView;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Storage;

import java.util.ArrayList;
import java.util.List;

public class StorageAdapterView extends RecyclerView.Adapter<StorageAdapterView.StorageHolder> {
    private List<Storage> storageList = new ArrayList<>();

    @NonNull
    @Override
    public StorageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storage_view_1, viewGroup, false);
        return new StorageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageHolder storageHolder, int i) {
        Storage currentStorage = storageList.get(i);
        storageHolder.textViewType.setText(currentStorage.getType());
        storageHolder.textViewAmount.setText(String.valueOf(currentStorage.getAmount()));
        storageHolder.textViewDate.setText(currentStorage.getDate());
    }

    @Override
    public int getItemCount() {
        return storageList.size();
    }

    public void setStorage(List<Storage> storages) {
        this.storageList = storages;
        notifyDataSetChanged();
    }

    public Storage getStorageAt(int position) {
        return storageList.get(position);
    }

    class StorageHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewAmount;
        private TextView textViewDate;

        public StorageHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewDate = itemView.findViewById(R.id.text_view_date);

        }
    }
}
