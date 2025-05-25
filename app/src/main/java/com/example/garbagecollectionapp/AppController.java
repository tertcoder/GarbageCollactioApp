package com.example.garbagecollectionapp;

import android.app.Application;
import android.content.Context;

import com.example.garbagecollectionapp.utils.SharedPrefManager;

public class AppController extends Application {
    private static AppController mInstance;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mAppContext = getApplicationContext();
        SharedPrefManager.init(mAppContext);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}