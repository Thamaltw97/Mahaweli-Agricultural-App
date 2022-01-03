package com.example.marketappauth.harvest.models;

public class DailyDarvest {

    float Weight;
    String Date;

    public float getWeight() {
        return Weight;
    }

    public DailyDarvest(float weight, String date) {
        Weight = weight;
        Date = date;
    }

    public void setWeight(float weight) {
        Weight = weight;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
