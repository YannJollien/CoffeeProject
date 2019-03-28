package com.example.coffeeproject2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Plantation;

import java.util.ArrayList;
import java.util.List;

public class PlantationAdapterView extends RecyclerView.Adapter<PlantationAdapterView.PlantationHolder>  {

    private List<Plantation> plantationList = new ArrayList<>();

    @NonNull
    @Override
    public PlantationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.plantation_view_2,viewGroup,false);
        return new PlantationHolder(itemView);
    }

    public Plantation getStorageAt(int position) {
        return plantationList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantationHolder storageHolder, int i) {
        Plantation currentPlantation = plantationList.get(i);
        storageHolder.textViewType.setText(currentPlantation.getType());
        storageHolder.textViewHectare.setText(String.valueOf(currentPlantation.getHectare()));
        storageHolder.textViewDate.setText(currentPlantation.getDate());

    }

    @Override
    public int getItemCount() {
        return plantationList.size();
    }

    public void setPlantation(List<Plantation> plantations){
        this.plantationList = plantations;
        notifyDataSetChanged();
    }

    class PlantationHolder extends RecyclerView.ViewHolder{
        private TextView textViewType;
        private TextView textViewHectare;
        private TextView textViewDate;


        public PlantationHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewHectare = itemView.findViewById(R.id.text_view_hectare);
            textViewDate = itemView.findViewById(R.id.text_view_date);

        }
    }
}
