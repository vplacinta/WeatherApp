package com.internship.weatherapp;

import com.internship.weatherapp.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiInterface {

    @GET("staticweather")
    Call<WeatherResponse> getWeatherData(@Query("q") String q);
}
