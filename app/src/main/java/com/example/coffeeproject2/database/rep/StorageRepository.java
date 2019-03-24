package com.example.coffeeproject2.database.rep;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.coffeeproject2.database.StorageDatabase;
import com.example.coffeeproject2.database.dao.StorageDao;
import com.example.coffeeproject2.database.entity.Storage;

import java.util.List;

public class StorageRepository {

    private StorageDao storageDao;
    private LiveData<List<Storage>> allStorage;

    public  StorageRepository(Application application){
        StorageDatabase database = StorageDatabase.getInstance(application);
        storageDao = database.storageDao();
        allStorage = storageDao.getAllStorage();
    }

    public void insert(Storage storage){
        new InsertStorageAsyncTask(storageDao).execute(storage);
    }

    public LiveData<List<Storage>> getAllStorage(){
        return allStorage;
    }

    private static class InsertStorageAsyncTask extends AsyncTask<Storage,Void,Void>{
        private StorageDao storageDao;

        private InsertStorageAsyncTask(StorageDao storageDao){
            this.storageDao=storageDao;
        }

        @Override
        protected Void doInBackground(Storage... storages){
            storageDao.insertStorage(storages[0]);
            return null;
        }
    }
 }
