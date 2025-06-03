package com.example.garbagecollectionapp.api;

import android.os.Build;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String EMULATOR_BASE_URL = "http://10.0.2.2:8080/api/v1/";
    private static final String PHYSICAL_DEVICE_BASE_URL = "http://192.168.219.126:8080/api/v1/";
    private static Retrofit retrofit = null;

    private static String getBaseUrl() {
        if (Build.FINGERPRINT.startsWith("generic") || 
            Build.FINGERPRINT.startsWith("unknown") ||
            Build.MODEL.contains("google_sdk") ||
            Build.MODEL.contains("Emulator") ||
            Build.MODEL.contains("Android SDK built for x86")) {
            return EMULATOR_BASE_URL;
        }
        return PHYSICAL_DEVICE_BASE_URL;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            retrofit = new Retrofit.Builder()
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    public static ApiInterface getApiService() {
        return getClient().create(ApiInterface.class);
    }
}