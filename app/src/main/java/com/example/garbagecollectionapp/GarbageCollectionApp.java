package com.example.garbagecollectionapp;

import android.app.Application;

import com.example.garbagecollectionapp.utils.SharedPrefManager;

public class GarbageCollectionApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefManager.init(this);
    }
} 