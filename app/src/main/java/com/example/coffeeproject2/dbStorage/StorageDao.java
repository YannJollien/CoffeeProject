package com.example.coffeeproject2.dbStorage;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface StorageDao {

    @Insert
    void insertStorage(Storage storage);


}
