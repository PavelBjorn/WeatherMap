package com.fedor.pavel.weathermap.api;


import com.fedor.pavel.weathermap.models.WeatherModel;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;
import retrofit.Callback;

public interface API {

    @GET(APIConstants.API_PATH_CITY_WEATHER)
    void getCityWeather(@QueryMap Map<String, String> queryParams, Callback<WeatherModel> models);


    class APIConstants {

        /*Request params*/

        public static final String API_BASE_URL = "http://api.openweathermap.org/data/2.5";

        public static final String API_PATH_CITY_WEATHER = "/weather";

        public static final String API_PARAMS_APP_ID = "appid";

        public static final String API_PARAMS_UNITS = "units";

        public static final String API_PARAMS_UNITS_METRIC = "metric";

        public static final String API_PARAMS_CITY_ID = "id";

        public static final String API_APP_ID = "153229fe17e2026d006b4f4a58237073";

        public static final String API_LOAD_ICON_URL = "http://openweathermap.org/img/w/";


       /*Response params*/

        public static final String JSON_TEMP_MODEL_KEY = "main";

        public static final String JSON_TEMP_KEY = "temp";

        public static final String JSON_MIN_TEMP_KEY = "temp_min";

        public static final String JSON_MAX_TEMP_KEY = "temp_max";

        public static final String JSON_SKY_STATES_LIST_KEY = "weather";

        public static final String JSON_SKY_STATES_TITLE_KEY = "main";

        public static final String JSON_SKY_STATES_DESCRIPTION_KEY = "description";

        public static final String JSON_SKY_STATES_ICON_ID_KEY = "icon";

        

    }


}
