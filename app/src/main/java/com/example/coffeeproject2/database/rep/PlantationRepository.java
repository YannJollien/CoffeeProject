package com.example.coffeeproject2.database.rep;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.coffeeproject2.database.PlantationDatabase;
import com.example.coffeeproject2.database.dao.PlantationDao;
import com.example.coffeeproject2.database.entity.Plantation;

import java.util.List;

public class PlantationRepository {

    private PlantationDao plantationDao;
    private LiveData<List<Plantation>> allPlantation;

    public  PlantationRepository(Application application){
        PlantationDatabase database = PlantationDatabase.getInstance(application);
        plantationDao = database.plantationDao();
        allPlantation = plantationDao.getAllPlantation();
    }

    public void insert(Plantation plantation){
        new InsertPlantationAsyncTask(plantationDao).execute(plantation);
    }

    public LiveData<List<Plantation>> getAllPlantation(){
        return allPlantation;
    }

    private static class InsertPlantationAsyncTask extends AsyncTask<Plantation,Void,Void> {
        private PlantationDao plantationDao;

        private InsertPlantationAsyncTask(PlantationDao plantationDao){
            this.plantationDao=plantationDao;
        }

        @Override
        protected Void doInBackground(Plantation... plantations){
            plantationDao.insertPlantation(plantations[0]);
            return null;
        }
    }
}
