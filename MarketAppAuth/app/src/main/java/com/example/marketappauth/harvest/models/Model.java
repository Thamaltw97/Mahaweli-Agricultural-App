package com.example.marketappauth.harvest.models;

import java.lang.ref.WeakReference;

public class Model {

    String Date,Season ,Crop , Area , Price,Weight;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String yeild) {
       Weight = yeild;
    }

    public String getSeason() {
        return Season;
    }

    public void setSeason(String season) {
        Season = season;
    }

    public String getCrop() {
        return Crop;
    }

    public void setCrop(String crop) {
        Crop = crop;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
