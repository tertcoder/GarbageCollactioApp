package com.example.garbagecollectionapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Schedule {
    @SerializedName("id")
    private String id;

    @SerializedName("areaId")
    private String areaId;

    @SerializedName("type")
    private String type;

    @SerializedName("pickupDate")
    private Date pickupDate;

    @SerializedName("notes")
    private String notes;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}