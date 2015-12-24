package com.fedor.pavel.weathermap.data;


import android.content.Context;
import android.content.SharedPreferences;

import com.fedor.pavel.weathermap.models.CityModel;

public class PreferencesManager {


    private static PreferencesManager instance;
    private static Context context;

    private static final String APP_SETTINGS_FILE_NAME = "app_settings";


    private PreferencesManager() {

    }

    public static PreferencesManager getInstance() {

        if (instance == null) {
            instance = new PreferencesManager();
        }

        return instance;
    }

    public static void initialize(Context context) {
        PreferencesManager.context = context;
    }


    public void saveCity(CityModel city) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SETTINGS_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(CityModel.SAVE_ID_KEY, city.getId());

        editor.putString(CityModel.SAVE_NAME_KEY, city.getName());

        editor.commit();

    }

    public CityModel loadCity() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_SETTINGS_FILE_NAME, context.MODE_PRIVATE);

        return new CityModel(sharedPreferences.getString(CityModel.SAVE_NAME_KEY, "Dnepropetrovsk"),
                sharedPreferences.getLong(CityModel.SAVE_ID_KEY, 694165));

    }


}
