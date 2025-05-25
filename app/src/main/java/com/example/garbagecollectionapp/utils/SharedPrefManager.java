package com.example.garbagecollectionapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.garbagecollectionapp.models.User;
import com.google.gson.Gson;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "garbage_collection_pref";
    private static final String KEY_USER = "user";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private static SharedPrefManager instance;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized SharedPrefManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SharedPrefManager not initialized. Call init() first.");
        }
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
    }

    public void saveUser(User user) {
        String userJson = gson.toJson(user);
        sharedPreferences.edit()
                .putString(KEY_USER, userJson)
                .apply();
    }

    public User getUser() {
        String userJson = sharedPreferences.getString(KEY_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, User.class);
        }
        return null;
    }

    public void saveToken(String token) {
        sharedPreferences.edit()
                .putString(KEY_TOKEN, token)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply();
    }

    public void saveRefreshToken(String refreshToken) {
        sharedPreferences.edit()
                .putString(KEY_REFRESH_TOKEN, refreshToken)
                .apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public String getRefreshToken() {
        return sharedPreferences.getString(KEY_REFRESH_TOKEN, null);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        sharedPreferences.edit()
                .remove(KEY_USER)
                .remove(KEY_TOKEN)
                .remove(KEY_REFRESH_TOKEN)
                .putBoolean(KEY_IS_LOGGED_IN, false)
                .apply();
    }
}