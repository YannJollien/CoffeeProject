package com.example.coffeeproject2.database.rep;

import android.arch.lifecycle.LiveData;

import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.database.fireabse.StorageListLiveData;
import com.example.coffeeproject2.database.fireabse.StorageLiveData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StorageRepository {

    private static final String TAG = "StorageRepository";
    private static StorageRepository instance;

    private StorageRepository() {}

    public static StorageRepository getInstance() {
        if (instance == null) {
            synchronized (StorageRepository.class) {
                if (instance == null) {
                    instance = new StorageRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Storage> getShow(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("storage")
                .child(name);
        return new StorageLiveData(reference);
    }

    public LiveData<List<Storage>> getAllShows() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("storage");
        return new StorageListLiveData(reference);
    }

    public void insert(final Storage show, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("storage")
                .child(show.getName())
                .setValue(show, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Show show, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("storage")
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
                .getReference("storage")
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
