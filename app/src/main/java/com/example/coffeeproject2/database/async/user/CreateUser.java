package com.example.coffeeproject2.database.async.user;

import android.app.Application;
import android.os.AsyncTask;

import com.example.coffeeproject2.BaseApp;
import com.example.coffeeproject2.database.entity.User;
import com.example.coffeeproject2.util.OnAsyncEventListener;


public class CreateUser extends AsyncTask<User, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateUser(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(User... params) {
        try {
            for (User account : params)
                ((BaseApp) application).getDatabase().userDao()
                        .insertUser(account);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}