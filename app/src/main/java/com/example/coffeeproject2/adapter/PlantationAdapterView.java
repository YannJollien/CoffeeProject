package com.example.coffeeproject2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Plantation;

import java.util.List;

public class PlantationAdapterView extends RecyclerView.Adapter<PlantationAdapterView.PlantationHolder> {

    private Context context;
    List<Plantation> plantationList;

    public PlantationAdapterView(Context context, List<Plantation> plantationList){
        this.context=context;
        this.plantationList = plantationList;
    }

    @NonNull
    @Override
    public PlantationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.plantation_view_2, viewGroup, false);
        return new PlantationAdapterView.PlantationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantationHolder plantationHolder, int i) {
        Plantation plantation = plantationList.get(i);
        plantationHolder.type.setText(plantation.getType());
        plantationHolder.hectare.setText(String.valueOf(plantation.getHectare()));
        plantationHolder.date.setText(plantation.getDate());
    }

    @Override
    public int getItemCount() {
        return plantationList.size();
    }

    public class PlantationHolder extends RecyclerView.ViewHolder{
        public TextView type;
        public TextView hectare;
        public TextView date;

        public PlantationHolder(View itemView){
            super(itemView);

            type = itemView.findViewById(R.id.text_view_type);
            hectare = itemView.findViewById(R.id.text_view_hectare);
            date = itemView.findViewById(R.id.text_view_date);

        }

    }
}
