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

//Plantation adapter
public class PlantationAdapter extends RecyclerView.Adapter<PlantationAdapter.PlantationHolder> {

    private List<Plantation> plantationList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public PlantationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.plantation_view_2, viewGroup, false);
        return new PlantationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantationHolder plantationHolder, int i) {
        Plantation currentPlantation = plantationList.get(i);
        plantationHolder.textViewType.setText(currentPlantation.getType());
        plantationHolder.textViewHectare.setText(String.valueOf(currentPlantation.getHectare()));
        plantationHolder.textViewDate.setText(currentPlantation.getDate());
    }

    @Override
    public int getItemCount() {
        return plantationList.size();
    }

    public void setPlantation(List<Plantation> plantations) {
        this.plantationList = plantations;
        notifyDataSetChanged();
    }

    public Plantation getPlantationAt(int position) {
        return plantationList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Plantation plantation);
    }

    class PlantationHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewHectare;
        private TextView textViewDate;
        private ImageView editView;


        //Holding the data
        private PlantationHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewHectare = itemView.findViewById(R.id.text_view_hectare);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            editView = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(plantationList.get(position));
                    // }
                }
            });

        }
    }
}
