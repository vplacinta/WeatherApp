package com.internship.weatherapp.interfaces;

import com.internship.weatherapp.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("staticweather")
    Call<WeatherResponse> getWeatherData(@Query("q") String q);
}
