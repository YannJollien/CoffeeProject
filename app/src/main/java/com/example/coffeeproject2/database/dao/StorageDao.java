package com.example.coffeeproject2.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.coffeeproject2.database.entity.Storage;

import java.util.List;


@Dao
public interface StorageDao {

    @Insert
    void insertStorage(Storage storage);

    //With lifedata we can Observer object
    @Query("Select * from storage")
    LiveData<List<Storage>> getAllStorage();


}
