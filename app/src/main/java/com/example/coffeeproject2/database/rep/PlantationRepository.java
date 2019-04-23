package com.example.coffeeproject2.database.rep;

import android.arch.lifecycle.LiveData;

import com.example.coffeeproject2.database.entity.Plantation;
import com.example.coffeeproject2.database.fireabse.PlantationListLiveData;
import com.example.coffeeproject2.database.fireabse.PlantationLiveData;
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

    public LiveData<Plantation> getShow(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(name);
        return new PlantationLiveData(reference);
    }

    public LiveData<List<Plantation>> getAllShows() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("plantation");
        return new PlantationListLiveData(reference);
    }

    public void insert(final Plantation show, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(show.getName())
                .setValue(show, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Plantation show, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(show.getName())
                .updateChildren(show.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Show show, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("plantation")
                .child(show.getName())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
