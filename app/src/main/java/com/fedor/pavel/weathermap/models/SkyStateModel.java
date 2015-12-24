package com.fedor.pavel.weathermap.models;


import com.fedor.pavel.weathermap.api.API;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SkyStateModel {


    @SerializedName(API.APIConstants.JSON_SKY_STATES_TITLE_KEY)
    private String title;

    @SerializedName(API.APIConstants.JSON_SKY_STATES_ICON_ID_KEY)
    private String iconId;

    @SerializedName(API.APIConstants.JSON_SKY_STATES_DESCRIPTION_KEY)
    private String description;


    public SkyStateModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
