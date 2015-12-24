package com.fedor.pavel.weathermap;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fedor.pavel.weathermap.api.API;
import com.fedor.pavel.weathermap.data.PreferencesManager;
import com.fedor.pavel.weathermap.models.WeatherModel;
import com.fedor.pavel.weathermap.network.ApiRequest;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class WeatherActivity extends AppCompatActivity implements Callback<WeatherModel> {


    private static final String LOG_TAG = "WeatherActivity";

    private static final String ACTION_BROADCAST = "com.fedor.pavel.START_UPDATE";

    private TextView tvCity, tvTem, tvTempMinMax, tvSkyStateDescription;

    private LinearLayout llContent, llErrorContent;

    private ImageView imvSkyState;

    private ProgressDialog progressDialog;

    private static final int REQUEST_CODE_SETTINGS = 1;

    private BroadcastReceiver broadcastReceiver;

    private static final long TRIGGER_TIME = 30 * 1000 * 60;

    private static final int REPEAT_TIME = 30 * 1000 * 60;

    private WeatherModel weatherModel;

    public static final String SAVE_STATE_WEATHER_MODEL_KEY = "weatherModel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather);

        prepareToolbar();

        findViews();

        tvCity.setText(PreferencesManager.getInstance().loadCity().toString());

        createProgressDialog();


        if (savedInstanceState == null) {

            progressDialog.show();

            ApiRequest.getWeatherData(this, this);

        } else {

            weatherModel = new Gson().fromJson(savedInstanceState.getString(SAVE_STATE_WEATHER_MODEL_KEY), WeatherModel.class);

            loadState(weatherModel);
        }


        prepareAlarmManager();

        registerLoadReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void findViews() {

        tvCity = (TextView) findViewById(R.id.weather_activity_tv_city);

        tvTem = (TextView) findViewById(R.id.weather_activity_tv_temp);

        tvTempMinMax = (TextView) findViewById(R.id.weather_activity_tv_tempMinMax);

        llErrorContent = (LinearLayout) findViewById(R.id.weather_activity_ll_errorContent);

        llContent = (LinearLayout) findViewById(R.id.weather_activity_ll_content);

        tvSkyStateDescription = (TextView) findViewById(R.id.weather_activity_tv_skyState);

        imvSkyState = (ImageView) findViewById(R.id.weather_activity_imv_skyStateIcon);

    }

    public static DisplayImageOptions displayImageOptions() {

        return new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.weather_activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_settings:

                Intent intent = new Intent(this, SettingsActivity.class);

                startActivityForResult(intent, REQUEST_CODE_SETTINGS);

                break;


        }

        return true;
    }

    @Override
    public void success(WeatherModel weatherModel, Response response) {

        this.weatherModel = weatherModel;

        loadState(weatherModel);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        showConnectionError(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (weatherModel != null) {
            outState.putString(SAVE_STATE_WEATHER_MODEL_KEY, new Gson().toJson(weatherModel));
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if (progressDialog.isShowing()) {

            progressDialog.dismiss();

            Log.d(LOG_TAG, "error" + "" + error);

            showConnectionError(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            progressDialog.show();
            ApiRequest.getWeatherData(this, this);
            tvCity.setText(PreferencesManager.getInstance().loadCity().toString());
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void prepareAlarmManager() {

        Intent intent = new Intent();
        intent.setAction(ACTION_BROADCAST);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                TRIGGER_TIME, REPEAT_TIME, alarmIntent);

    }

    private void registerLoadReceiver() {

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
                wl.acquire();
                progressDialog.show();
                ApiRequest.getWeatherData(WeatherActivity.this, WeatherActivity.this);

                wl.release();

            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(ACTION_BROADCAST));

    }

    private void showConnectionError(boolean isConnected) {

        if (isConnected) {

            llContent.setVisibility(View.VISIBLE);
            llErrorContent.setVisibility(View.GONE);

            return;
        }


        llContent.setVisibility(View.GONE);
        llErrorContent.setVisibility(View.VISIBLE);

        return;

    }

    public void tryAgainClick(View view) {

        progressDialog.show();
        ApiRequest.getWeatherData(this, this);

    }

    private void loadState(WeatherModel weatherModel) {

        if (weatherModel != null) {

            double temp = weatherModel.getTemp().getTemp();

            double tempMin = weatherModel.getTemp().getMinTemp();

            double tempMax = weatherModel.getTemp().getMaxTemp();

            tvTem.setText((temp >= 0 ? "+" : "-") + temp + "°");

            String str = (tempMin >= 0 ? "+" : "-");

            tvTempMinMax.setText("(" + str + tempMin + "°"
                    + "  " + str +
                    +tempMax + "°)");

            tvSkyStateDescription.setText(weatherModel.getSkyState(0).getDescription());

            ImageLoader.getInstance().displayImage(API.APIConstants.API_LOAD_ICON_URL
                    + weatherModel.getSkyState(0).getIconId() + ".png"
                    , imvSkyState
                    , displayImageOptions());

        }

    }

    private void createProgressDialog() {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading data");

        progressDialog.setCancelable(false);
    }
}
