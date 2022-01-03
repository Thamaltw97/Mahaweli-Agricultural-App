package com.example.marketappauth.healthiness.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Healthiness {
    private String userId;
    private String crop;
    private String date;

    public Healthiness() {
    }

    public Healthiness(String userId, String crop, String date) {
        this.userId = userId;
        this.crop = crop;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
