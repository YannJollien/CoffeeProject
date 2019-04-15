package com.example.coffeeproject2.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


public class Plantation {

    private String id;

    private String type;

    private double hectare;

    private String date;

    public Plantation(){

    }
    //Constructor
    public Plantation(String type, double hectare, String date) {
        this.type = type;
        this.hectare = hectare;
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHectare(double amount) {
        this.hectare = hectare;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getHectare() {
        return hectare;
    }

    public String getDate() {
        return date;
    }
}
