package com.example.coffeeproject2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Storage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.StorageHolder> {

    private List<Storage> storageList = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public StorageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.storage_view, viewGroup, false);
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
        public ImageView editView;


        public StorageHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            editView = itemView.findViewById(R.id.image_edit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(storageList.get(position));
                   // }
                }
            });
/**
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(storageList.get(position));
                    }
                }


            });
*/

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Storage storage);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
