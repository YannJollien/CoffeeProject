package com.example.coffeeproject2.database.fireabse;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.coffeeproject2.database.entity.Plantation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlantationListLiveData extends LiveData<List<Plantation>> {

    private static final String TAG = "PlantationListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public PlantationListLiveData(DatabaseReference reference, String showName) {
        this.reference= reference;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toAccounts(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Plantation> toAccounts(DataSnapshot snapshot) {
        List<Plantation> plantations = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Plantation entity = childSnapshot.getValue(Plantation.class);
            entity.setId(childSnapshot.getKey());
            plantations.add(entity);
        }
        return plantations;
    }
}
