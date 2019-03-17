package com.example.coffeeproject2.dbStorage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Storage.class}, version = 1)
public abstract class StorageDatabase extends RoomDatabase {

    public abstract StorageDao storageDao();

}
