package com.example.coffeeproject2.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.coffeeproject2.database.dao.StorageDao;
import com.example.coffeeproject2.database.entity.Storage;


@Database(entities = {Storage.class}, version = 1)
public abstract class StorageDatabase extends RoomDatabase {

    private static StorageDatabase instance;

    public abstract StorageDao storageDao();

    public static synchronized StorageDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),StorageDatabase.class,"storage_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //Um Testdaten einzufügen
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private StorageDao storageDao;

        private PopulateDbAsyncTask(StorageDatabase storageDatabase){
            storageDao = storageDatabase.storageDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            storageDao.insertStorage(new Storage("Arabica",123,"11.01.2015"));
            storageDao.insertStorage(new Storage("Arabica",154,"11.01.2018"));
            storageDao.insertStorage(new Storage("Arabica",54,"11.01.2012"));
            return null;
        }
    }

}
