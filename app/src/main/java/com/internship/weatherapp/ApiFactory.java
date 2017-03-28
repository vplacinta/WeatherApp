package com.internship.weatherapp;

import com.internship.weatherapp.api.WeatherAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiFactory {

    public static WeatherAPI createAPI() {
         Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://andfun-weather.udacity.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         return  retrofit.create(WeatherAPI.class);
    }
}


