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

    Context context;
    ArrayList<Storage> storage;

    public StorageAdapterView(Context context, ArrayList<Storage> storage){
        this.context=context;
        this.storage=storage;
    }


    @NonNull
    @Override
    public StorageAdapterView.StorageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StorageHolder(LayoutInflater.from(context).inflate(R.layout.storage_view_1,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StorageAdapterView.StorageHolder storageHolder, int i) {
        storageHolder.type.setText(storage.get(i).getType());
        storageHolder.amount.setText(String.valueOf(storage.get(i).getAmount()));
        storageHolder.date.setText(storage.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return storage.size();
    }

    class StorageHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView amount;
        TextView date;

        public StorageHolder(View view){
            super(view);
            type = view.findViewById(R.id.text_view_type);
            amount = view.findViewById(R.id.text_view_amount);
            date = view.findViewById(R.id.text_view_date);
        }

    }
}


