package com.internship.weatherapp;

import com.internship.weatherapp.models.WeatherItem;

import java.util.List;

public class WeatherStorage {
    private List<WeatherItem> list;

    private static WeatherStorage instance;
    private WeatherStorage () {}

    public static WeatherStorage getInstance() {
        if (instance == null) {
            instance = new WeatherStorage ();
        }
        return instance;
    }

    public List<WeatherItem> getList() {
        return list;
    }

    public void setList(List<WeatherItem> list) {
        this.list = list;
    }
}