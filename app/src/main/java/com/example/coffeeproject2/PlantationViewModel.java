package com.example.coffeeproject2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.coffeeproject2.database.entity.Plantation;
import com.example.coffeeproject2.database.rep.PlantationRepository;

import java.util.List;

//Plantation ViewModel
public class PlantationViewModel extends AndroidViewModel {

    private PlantationRepository repository;
    private LiveData<List<Plantation>> allPlantation;

    public PlantationViewModel(@NonNull Application application) {
        super(application);
        repository = new PlantationRepository(application);
        allPlantation = repository.getAllPlantation();
    }

    public void insert(Plantation plantation) {
        repository.insert(plantation);
    }

    public void update(Plantation plantation) {
        repository.update(plantation);
    }

    public void delete(Plantation plantation) {
        repository.delete(plantation);
    }

    public void deleteAllPlantation() {
        repository.deleteAllStorage();
    }

    public LiveData<List<Plantation>> getAllPlantation() {
        return allPlantation;
    }
}
