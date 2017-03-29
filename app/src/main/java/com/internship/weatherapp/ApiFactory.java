package com.internship.weatherapp;

import android.content.Context;

import com.internship.weatherapp.api.WeatherAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiFactory {

    private Context context;

    public WeatherAPI createAPI(Context context) {
        this.context = context;

         Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         return  retrofit.create(WeatherAPI.class);
    }
}


