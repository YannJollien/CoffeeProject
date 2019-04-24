package com.example.coffeeproject2.database.rep;

import android.arch.lifecycle.LiveData;

import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.database.fireabse.StorageListLiveData;
import com.example.coffeeproject2.database.fireabse.StorageLiveData;
import com.example.coffeeproject2.util.OnAsyncEventListener;
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

    public LiveData<Storage> getStorage(final String name) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("storage")
                .child(name);
        return new StorageLiveData(reference);
    }

    public StorageListLiveData getAllStorages() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("storage");
        return new StorageListLiveData(reference);
    }

    public void insert(final Storage storage, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("storage")
                .child(storage.getId())
                .setValue(storage, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final Storage storage, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("storage")
                .child(storage.getId())
                .updateChildren(storage.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final Storage storage, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("storage")
                .child(storage.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public LiveData<List<Storage>> getAllEpisodes(String showName) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("storage");
        return new StorageListLiveData(reference);
    }
}
