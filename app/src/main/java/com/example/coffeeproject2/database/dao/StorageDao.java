package com.example.coffeeproject2.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.coffeeproject2.database.entity.Storage;


@Dao
public interface StorageDao {

    @Insert
    void insertStorage(Storage storage);


}
