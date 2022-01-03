package com.example.marketappauth.harvest.models;

public class ModelHarvest {

    String Season ,Crop , Area ,Temp,Year,Index;
    Long Rainfall, Yield;


    public Long getRainfall() {
        return Rainfall;
    }

    public void setRainfall(Long rainfall) {
        Rainfall = rainfall;
    }

    public Long getYield() {
        return Yield;
    }

    public void setYield(Long yield) {
        Yield = yield;
    }

    public String getTemp() { return Temp; }



    public void setTemp(String temp) { Temp = temp; }


    public String getYear() { return Year; }

    public void setYear(String year) {Year = year; }



    public String getIndex() { return Index; }

    public void setIndex(String index) { Index = index; }



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


}
