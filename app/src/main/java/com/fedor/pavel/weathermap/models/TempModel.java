package com.fedor.pavel.weathermap.models;


import com.fedor.pavel.weathermap.api.API;
import com.google.gson.annotations.SerializedName;

public class TempModel {


    @SerializedName(API.APIConstants.JSON_TEMP_KEY)
    private double temp;

    @SerializedName(API.APIConstants.JSON_MIN_TEMP_KEY)
    private double minTemp;

    @SerializedName(API.APIConstants.JSON_MAX_TEMP_KEY)
    private double maxTemp;


    public TempModel() {

    }

    public TempModel(double temp, double minTemp, double maxTemp) {
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

}
