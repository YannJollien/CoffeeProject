package com.example.coffeeproject2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.coffeeproject2.database.entity.Storage;
import com.example.coffeeproject2.database.rep.StorageRepository;

import java.util.List;

public class StorageViewModel extends AndroidViewModel {

    private StorageRepository repository;
    private LiveData<List<Storage>> allStorage;

    public StorageViewModel(@NonNull Application application) {
        super(application);
        repository = new StorageRepository(application);
        allStorage = repository.getAllStorage();
    }

    public void insert(Storage storage){
        repository.insert(storage);
    }

    public void update(Storage storage){
        repository.update(storage);
    }

    public void delete(Storage storage){
        repository.delete(storage);
    }

    public void deleteAllStorage(){
        repository.deleteAllStorage();
    }

    public LiveData<List<Storage>> getAllStorage(){
        return allStorage;
    }
}
