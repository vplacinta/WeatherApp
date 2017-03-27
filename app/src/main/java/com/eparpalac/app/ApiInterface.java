package com.eparpalac.app;

import com.eparpalac.app.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface ApiInterface {

    @GET("?")
    Call<WeatherResponse> getWeatherData(@Query("q") String q);
}
