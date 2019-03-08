package com.example.coffeeproject2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "Users")
public class User {
    @PrimaryKey
    public int UserId;

    @ColumnInfo(name = "FirstName")
    public String firstName;

    @ColumnInfo(name = "LastName")
    public String lastName;

    @ColumnInfo(name = "Email")
    public String email;

    @ColumnInfo(name = "Password")
    public String password;

    @ColumnInfo(name = "State")
    public String state;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
