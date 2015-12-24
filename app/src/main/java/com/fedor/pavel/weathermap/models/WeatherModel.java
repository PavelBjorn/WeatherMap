package com.fedor.pavel.weathermap.models;


import com.fedor.pavel.weathermap.api.API;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherModel {


    @SerializedName(API.APIConstants.JSON_TEMP_MODEL_KEY)
    private TempModel temp;

    @SerializedName(API.APIConstants.JSON_SKY_STATES_LIST_KEY)
    private ArrayList<SkyStateModel> skyStateList = new ArrayList<>();

    public TempModel getTemp() {

        return temp;

    }

    public void setTemp(TempModel temp) {
        this.temp = temp;
    }

    public SkyStateModel getSkyState(int position) {

        return skyStateList.get(position);

    }

    public void addSkyState(SkyStateModel skyState) {

        skyStateList.add(skyState);

    }


}
