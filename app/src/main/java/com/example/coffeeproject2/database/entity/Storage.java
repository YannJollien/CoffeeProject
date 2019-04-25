package com.example.coffeeproject2.database.entity;

import java.util.HashMap;
import java.util.Map;

//Storage entity
public class Storage {

    private String id;

    private String type;

    private double amount;

    private String date;

    public Storage(){

    }
    //Constructor
    public Storage(String id, String type, double amount, String date) {
        this.id=id;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public Storage(String type, double amount, String date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("type", type);
        result.put("amount", amount);
        result.put("id", id);
        result.put("date", date);

        return result;
    }
}
