package com.example.coffeeproject2.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.coffeeproject2.database.dao.StorageDao;
import com.example.coffeeproject2.database.entity.Storage;


@Database(entities = {Storage.class}, version = 1)
public abstract class StorageDatabase extends RoomDatabase {

    public abstract StorageDao storageDao();

}
