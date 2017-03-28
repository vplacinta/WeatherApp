package com.internship.weatherapp.api;

import com.internship.weatherapp.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface WeatherAPI {

    @GET("/staticweather")
    Call<WeatherResponse> getData(
                                  @Query("q") String city,
                                  @Query("mode") String mode,
                                  @Query("units") String units,
                                  @Query("cnt") int dayCount
                                  );
}