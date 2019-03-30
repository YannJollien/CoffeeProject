package com.example.coffeeproject2.util;


public interface OnAsyncEventListener {
    void onSuccess();

    void onFailure(Exception e);
}