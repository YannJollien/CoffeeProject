package com.example.coffeeproject2.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.coffeeproject2.database.entity.Storage;

import java.util.List;


@Dao
public interface StorageDao {

    @Insert
    void insertStorage(Storage storage);

    @Update
    void updateStorage(Storage storage);

    @Delete
    void deleteStorage(Storage storage);

    @Query("Delete FROM storage")
    void deleteAllStorage();

    //With lifedata we can Observer object
    @Query("Select * from storage")
    LiveData<List<Storage>> getAllStorage();

}
