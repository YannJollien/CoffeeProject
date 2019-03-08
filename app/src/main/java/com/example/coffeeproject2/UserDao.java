package com.example.coffeeproject2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User users);



}
