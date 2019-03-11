package com.example.coffeeproject2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User users);

    @Query("SELECT last_name FROM users where email = :email")
    List<String> loadUsersName(String email);



}
