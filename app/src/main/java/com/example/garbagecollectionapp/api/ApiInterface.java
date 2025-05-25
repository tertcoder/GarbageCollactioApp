package com.example.garbagecollectionapp.api;

import com.example.garbagecollectionapp.models.Area;
import com.example.garbagecollectionapp.models.Schedule;
import com.example.garbagecollectionapp.models.SpecialRequest;
import com.example.garbagecollectionapp.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    // Authentication
    @POST("auth/register")
    Call<ResponseBody> registerUser(
            @Body User user
    );

    @POST("auth/login")
    Call<ResponseBody> loginUser(
            @Body User user
    );

    // User Management
    @GET("users/me")
    Call<ResponseBody> getCurrentUser(
            @Header("Authorization") String token
    );

    // Area Management
    @GET("areas/all")
    Call<ResponseBody> getAllAreas(
            @Header("Authorization") String token
    );

    @GET("areas/{id}")
    Call<ResponseBody> getAreaById(
            @Header("Authorization") String token,
            @Path("id") String id
    );

    @GET("areas/zone/{zone}")
    Call<ResponseBody> getAreasByZone(
            @Header("Authorization") String token,
            @Path("zone") String zone
    );

    // Schedule Management
    @GET("schedules")
    Call<ResponseBody> getAllSchedules(
            @Header("Authorization") String token,
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("schedules/area/{areaId}")
    Call<ResponseBody> getSchedulesByArea(
            @Header("Authorization") String token,
            @Path("areaId") String areaId,
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("schedules/date-range")
    Call<ResponseBody> getSchedulesByDateRange(
            @Header("Authorization") String token,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );

    @POST("schedules/filter")
    Call<ResponseBody> filterSchedules(
            @Header("Authorization") String token,
            @Body Object filter
    );

    // Special Requests
    @POST("special-requests")
    Call<ResponseBody> createSpecialRequest(
            @Header("Authorization") String token,
            @Body SpecialRequest request
    );

    @GET("special-requests/user")
    Call<ResponseBody> getUserRequests(
            @Header("Authorization") String token,
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("special-requests/{id}")
    Call<ResponseBody> getRequestById(
            @Header("Authorization") String token,
            @Path("id") String id
    );

    @PATCH("special-requests/{id}/cancel")
    Call<ResponseBody> cancelRequest(
            @Header("Authorization") String token,
            @Path("id") String id
    );

    @POST("special-requests/filter")
    Call<ResponseBody> filterRequests(
            @Header("Authorization") String token,
            @Body Object filter
    );

    // Dashboard
    @GET("dashboard/user/stats")
    Call<ResponseBody> getUserDashboardStats(
            @Header("Authorization") String token
    );
}