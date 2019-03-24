package com.example.coffeeproject2.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "plantation")
public class Plantation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "hectare")
    private double hectare;

    @ColumnInfo(name = "date")
    private String date;

    public Plantation(String type, double hectare, String date) {
        this.type = type;
        this.hectare = hectare;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getHectare() {
        return hectare;
    }

    public void setHectare(double hectare) {
        this.hectare = hectare;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
