package com.example.coffeeproject2.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User users);

    /*@Query("SELECT password FROM users where email = :email")
    List<String> loadUsersName(String email);*/

    @Query("SELECT Email from users where Email = :email and Password = :pass")
    List<String> loadUsersName (String email,String pass);

}
