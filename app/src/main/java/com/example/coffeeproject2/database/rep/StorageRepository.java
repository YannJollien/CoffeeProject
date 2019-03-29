package com.example.coffeeproject2.database.rep;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.coffeeproject2.database.StorageDatabase;
import com.example.coffeeproject2.database.dao.StorageDao;
import com.example.coffeeproject2.database.entity.Storage;

import java.util.List;

//Rep for storage
public class StorageRepository {

    //Reference for Dan and List of storage
    private StorageDao storageDao;
    private LiveData<List<Storage>> allStorage;

    public StorageRepository(Application application){
        StorageDatabase database = StorageDatabase.getInstance(application);
        storageDao = database.storageDao();
        //getting all the storage
        allStorage = storageDao.getAllStorage();
    }

    //Operation methods useing async classes
    public void insert(Storage storage){
        new InsertStorageAsyncTask(storageDao).execute(storage);
    }

    public void delete(Storage storage){
        new DeleteStorageAsyncTask(storageDao).execute(storage);
    }

    public void update(Storage storage){
        new UpdateStorageAsyncTask(storageDao).execute(storage);
    }

    public void deleteAllStorage(){
        new DeleteAllStorageAsyncTask(storageDao).execute();
    }

    public LiveData<List<Storage>> getAllStorage(){
        return allStorage;
    }


    //All classes as inner class to have everything synchronous
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

    private static class UpdateStorageAsyncTask extends AsyncTask<Storage,Void,Void>{
        private StorageDao storageDao;

        private UpdateStorageAsyncTask(StorageDao storageDao){
            this.storageDao=storageDao;
        }

        @Override
        protected Void doInBackground(Storage... storages){
            storageDao.updateStorage(storages[0]);
            return null;
        }
    }

    private static class DeleteStorageAsyncTask extends AsyncTask<Storage,Void,Void>{
        private StorageDao storageDao;

        private DeleteStorageAsyncTask(StorageDao storageDao){
            this.storageDao=storageDao;
        }

        @Override
        protected Void doInBackground(Storage... storages){
            storageDao.deleteStorage(storages[0]);
            return null;
        }
    }

    private static class DeleteAllStorageAsyncTask extends AsyncTask<Void,Void,Void>{
        private StorageDao storageDao;

        private DeleteAllStorageAsyncTask(StorageDao storageDao){
            this.storageDao=storageDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            storageDao.deleteAllStorage();
            return null;
        }
    }
 }
