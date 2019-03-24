package com.example.coffeeproject2.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.coffeeproject2.database.entity.Plantation;

import java.util.List;

@Dao
public interface PlantationDao {

    @Insert
    void insertPlantation(Plantation plantation);

    @Update
    void updatePlantation(Plantation plantation);

    @Delete
    void deletePlantation(Plantation plantation);

    @Query("Delete FROM plantation")
    void deleteAllPlantation();

    //With lifedata we can Observer object
    @Query("Select * from plantation")
    LiveData<List<Plantation>> getAllPlantation();

}
