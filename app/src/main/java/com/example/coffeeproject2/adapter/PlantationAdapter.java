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
import com.example.coffeeproject2.database.entity.Storage;

import java.util.ArrayList;
import java.util.List;

public class PlantationAdapter extends RecyclerView.Adapter<PlantationAdapter.PlantationHolder>  {

    private List<Plantation> plantationList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public PlantationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.plantation_view,viewGroup,false);
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
        public ImageView editView;

        public PlantationHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewHectare = itemView.findViewById(R.id.text_view_hectare);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            editView = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(plantationList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Plantation plantation);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
