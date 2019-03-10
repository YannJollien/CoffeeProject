package com.example.coffeeproject2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User users);

    @Query("SELECT * FROM users WHERE :email")
     User[] loadAllUsersWithMail(String email);



}
