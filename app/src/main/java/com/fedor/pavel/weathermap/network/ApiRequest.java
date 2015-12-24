package com.fedor.pavel.weathermap.network;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.fedor.pavel.weathermap.R;
import com.fedor.pavel.weathermap.api.API;
import com.fedor.pavel.weathermap.data.PreferencesManager;
import com.fedor.pavel.weathermap.models.WeatherModel;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApiRequest {


    public static void getWeatherData(Activity activity, Callback<WeatherModel> callback) {


        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() == null) {

            createSettingsDialog(activity);

            return;
        }


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API.APIConstants.API_BASE_URL).build();

        API api = restAdapter.create(API.class);

        HashMap<String, String> queryParams = new HashMap<>();

        queryParams.put(API.APIConstants.API_PARAMS_CITY_ID, "" + PreferencesManager.getInstance().loadCity().getId());

        queryParams.put(API.APIConstants.API_PARAMS_APP_ID, API.APIConstants.API_APP_ID);

        queryParams.put(API.APIConstants.API_PARAMS_UNITS, API.APIConstants.API_PARAMS_UNITS_METRIC);

        api.getCityWeather(queryParams, callback);

    }

    public static void createSettingsDialog(final Activity activity) {

        AlertDialog.Builder settingsAlert = new AlertDialog.Builder(activity);

        settingsAlert.setTitle(activity.getResources().getText(R.string.no_internet_alert_title));
        settingsAlert.setIcon(R.drawable.ic_alert);

        settingsAlert.setCancelable(false);


        settingsAlert.setMessage(activity.getResources().getText(R.string.no_internet_alert_message));

        settingsAlert.setPositiveButton(activity.getResources().getText(R.string.settings_dialog_positive_btn)
                , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        settingsAlert.setNegativeButton(activity.getResources().getText(R.string.settings_dialog_negative_btn)
                , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                activity.finish();

            }
        });


        settingsAlert.show();

    }
}
