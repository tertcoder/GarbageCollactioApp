package com.example.garbagecollectionapp.utils;

public class AppConstants {
    public static final String BASE_URL = "http://localhost:8080/api/v1/";
    public static final String PREF_NAME = "garbage_collection_pref";
    public static final String KEY_TOKEN = "key_token";
    public static final String KEY_USER = "key_user";
    public static final String KEY_IS_LOGGED_IN = "key_is_logged_in";

    public static final int REQUEST_TIMEOUT = 60;
    public static final int PAGE_SIZE = 10;

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_CANCELLED = "CANCELLED";

    public static final String TYPE_REGULAR = "REGULAR";
    public static final String TYPE_SPECIAL = "SPECIAL";
}