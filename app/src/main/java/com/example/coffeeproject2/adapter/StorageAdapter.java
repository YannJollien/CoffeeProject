package com.example.coffeeproject2.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffeeproject2.R;
import com.example.coffeeproject2.database.entity.Storage;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//Adapter for Storage
/*public class StorageAdapter extends ArrayAdapter<Storage> {

    private Activity context;
    private List<Storage> storageList;


    public StorageAdapter(Activity context, List<Storage> storageList){
        super(context, R.layout.storage_view_1,storageList);
        this.context=context;
        this.storageList=storageList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.storage_view_1, null, true);

        Storage storage = storageList.get(position);

        TextView type = listViewItem.findViewById(R.id.text_view_type);
        TextView amount = listViewItem.findViewById(R.id.text_view_amount);
        TextView date = listViewItem.findViewById(R.id.text_view_date);

        return listViewItem;
    }

}*/
