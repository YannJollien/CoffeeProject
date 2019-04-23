package com.example.coffeeproject2.database.fireabse;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.coffeeproject2.database.entity.Storage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StorageListLiveData extends LiveData<List<Storage>> {

    private static final String TAG = "StorageListLiveData";

    private final DatabaseReference reference;
    private final StorageListLiveData.MyValueEventListener listener = new StorageListLiveData.MyValueEventListener();

    public StorageListLiveData(DatabaseReference reference, String showName) {
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
            setValue(toStorages(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Storage> toStorages(DataSnapshot snapshot) {
        List<Storage> storages = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Storage entity = childSnapshot.getValue(Storage.class);
            entity.setId(childSnapshot.getKey());
            storages.add(entity);
        }
        return storages;
    }

}
