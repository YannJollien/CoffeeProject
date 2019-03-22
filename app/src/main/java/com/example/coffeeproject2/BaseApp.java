package com.example.coffeeproject2;

import android.app.Application;

import com.example.coffeeproject2.database.AppDatabase;

/**
 * Android Application class. Used for accessing singletons................
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

}