package com.example.coffeeproject2.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.coffeeproject2.database.dao.PlantationDao;
import com.example.coffeeproject2.database.entity.Plantation;

@Database(entities = {Plantation.class}, version = 1)
public abstract class PlantationDatabase extends RoomDatabase {

    private static PlantationDatabase instance;

    public abstract PlantationDao plantationDao();

    public static synchronized PlantationDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),PlantationDatabase.class,"plantation_database")
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

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private PlantationDao plantationDao;

        private PopulateDbAsyncTask(PlantationDatabase plantationDatabase){
            plantationDao = plantationDatabase.plantationDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            plantationDao.insertPlantation(new Plantation("Arabica",12,"11.01.2015"));
            plantationDao.insertPlantation(new Plantation("Arabica",13,"11.01.2013"));
            plantationDao.insertPlantation(new Plantation("Arabica",14,"11.01.2012"));
            return null;
        }
    }
}
