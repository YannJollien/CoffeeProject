package com.example.coffeeproject2.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.coffeeproject2.database.entity.User;

import java.util.List;

//Dad fpr user
@Dao
public interface UserDao {

    //Insert user
    @Insert
    void insertUser(User users);

    //Get email and password for login
    @Query("SELECT Email from users where Email = :email and Password = :pass")
    List<String> loadUsersName(String email, String pass);

    //Delete user
    @Query("Delete from users where Email = :email")
    void deleteUser(String email);

}
