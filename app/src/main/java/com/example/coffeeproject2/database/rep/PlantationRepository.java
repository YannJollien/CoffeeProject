package com.example.coffeeproject2.database.rep;

import android.arch.lifecycle.LiveData;

import com.example.coffeeproject2.database.entity.Plantation;
import com.example.coffeeproject2.database.fireabse.PlantationListLiveData;
import com.example.coffeeproject2.database.fireabse.PlantationLiveData;
import com.example.coffeeproject2.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PlantationRepository {

    private static final String TAG = "PlantationRepository";
    private static PlantationRepository instance;

    private PlantationRepository() {}

    public static PlantationRepository getInstance() {
        if (instance == null) {
            synchronized (PlantationRepository.class) {
                if (instance == null) {
                    instance = new PlantationRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Plantation> getPlantation(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(name);
        return new PlantationLiveData(reference);
    }

    public LiveData<List<Plantation>> getAllPlantations() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("plantation");
        return new PlantationListLiveData(reference);
    }

    public void insert(final Plantation plantation, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(plantation.getId())
                .setValue(plantation, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Plantation plantation, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(plantation.getId())
                .updateChildren(plantation.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Plantation plantation, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(plantation.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<Plantation>> getAllPlantation(String showName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("plantation");
        return new PlantationListLiveData(reference);
    }
}
