package com.example.coffeeproject2.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.coffeeproject2.database.dao.UserDao;
import com.example.coffeeproject2.database.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
