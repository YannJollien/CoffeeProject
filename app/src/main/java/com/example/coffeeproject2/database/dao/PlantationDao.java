package com.example.coffeeproject2.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.coffeeproject2.database.entity.Plantation;

import java.util.List;

@Dao
public interface PlantationDao {

    @Insert
    void insertPlantation(Plantation plantation);

    //With lifedata we can Observer object
    @Query("Select * from plantation")
    LiveData<List<Plantation>> getAllPlantation();

}
