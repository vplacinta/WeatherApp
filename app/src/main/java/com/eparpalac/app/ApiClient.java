package com.eparpalac.app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClient {

    private static final String BASE_URL = "https://andfun-weather.udacity.com/staticweather/";
    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
