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

    public void delete(Plantation plantation){
        new DeletePlantationAsyncTask(plantationDao).execute(plantation);
    }

    public void update(Plantation plantation){
        new UpdatePlantationAsyncTask(plantationDao).execute(plantation);
    }

    public void deleteAllStorage(){
        new DeleteAllPlantationAsyncTask(plantationDao).execute();
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

    private static class UpdatePlantationAsyncTask extends AsyncTask<Plantation,Void,Void>{
        private PlantationDao plantationDao;

        private UpdatePlantationAsyncTask(PlantationDao plantationDao){
            this.plantationDao=plantationDao;
        }

        @Override
        protected Void doInBackground(Plantation... plantations){
            plantationDao.updatePlantation(plantations[0]);
            return null;
        }
    }

    private static class DeletePlantationAsyncTask extends AsyncTask<Plantation,Void,Void>{
        private PlantationDao plantationDao;

        private DeletePlantationAsyncTask(PlantationDao plantationDao){
            this.plantationDao=plantationDao;
        }

        @Override
        protected Void doInBackground(Plantation... plantations){
            plantationDao.deletePlantation(plantations[0]);
            return null;
        }
    }

    private static class DeleteAllPlantationAsyncTask extends AsyncTask<Void,Void,Void>{
        private PlantationDao plantationDao;

        private DeleteAllPlantationAsyncTask(PlantationDao plantationDao){
            this.plantationDao=plantationDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            plantationDao.deleteAllPlantation();
            return null;
        }
    }
}
