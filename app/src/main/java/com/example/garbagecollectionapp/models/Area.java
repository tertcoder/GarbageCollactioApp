package com.example.garbagecollectionapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Area {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("zone")
    private String zone;

    @SerializedName("pickupDays")
    private List<String> pickupDays;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("updatedAt")
    private Date updatedAt;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public List<String> getPickupDays() {
        return pickupDays;
    }

    public void setPickupDays(List<String> pickupDays) {
        this.pickupDays = pickupDays;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}