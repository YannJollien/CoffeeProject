package com.example.coffeeproject2.database.fireabse;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.coffeeproject2.database.entity.Storage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class StorageLiveData extends LiveData<Storage> {

    private static final String TAG = "StorageLiveData";

    private final DatabaseReference reference;
    private final StorageLiveData.MyValueEventListener listener = new StorageLiveData.MyValueEventListener();

    public StorageLiveData(DatabaseReference reference) {
        this.reference = reference;
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
            Storage entity = dataSnapshot.getValue(Storage.class);
            try {
                entity.setId(dataSnapshot.getKey());
                setValue(entity);
            }
            catch (NullPointerException e){}
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

}
