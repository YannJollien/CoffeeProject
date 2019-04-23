package com.example.coffeeproject2;

import android.app.Application;

import com.example.coffeeproject2.database.rep.PlantationRepository;
import com.example.coffeeproject2.database.rep.StorageRepository;

public class BaseApp extends Application {

    public PlantationRepository getEpisodeRepository() {
        return PlantationRepository.getInstance();
    }

    public StorageRepository getShowRepository() {
        return StorageRepository.getInstance();
    }

}
