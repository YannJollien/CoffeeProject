package com.example.coffeeproject2.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Storage;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StorageAdapterView extends RecyclerView.Adapter<StorageAdapterView.StorageHolder> {

    private Context context;
    List<Storage> storageList;

    public StorageAdapterView(Context context, List<Storage> storageList){
        this.context=context;
        this.storageList=storageList;
    }

    @NonNull
    @Override
    public StorageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storage_view_1, viewGroup, false);
        return new StorageAdapterView.StorageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageHolder storageHolder, int i) {
        Storage storage = storageList.get(i);
        storageHolder.type.setText(storage.getType());
        storageHolder.amount.setText(String.valueOf(storage.getAmount()));
        storageHolder.date.setText(storage.getDate());
    }

    @Override
    public int getItemCount() {
        return storageList.size();
    }

    public class StorageHolder extends RecyclerView.ViewHolder{
        public TextView type;
        public TextView amount;
        public TextView date;

        public StorageHolder(View itemView){
            super(itemView);

            type = itemView.findViewById(R.id.text_view_type);
            amount = itemView.findViewById(R.id.text_view_amount);
            date = itemView.findViewById(R.id.text_view_date);

        }

    }

    public Storage getStorage(int position){
        Storage storage = storageList.get(position);
        return storage;
    }


}


