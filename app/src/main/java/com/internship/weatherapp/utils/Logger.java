package com.internship.weatherapp.utils;

import android.util.Log;

import com.internship.weatherapp.BuildConfig;

public class Logger {

    public static void logString(String tag, String value){

        if(BuildConfig.DEBUG) {
            Log.i(tag, value);
        }
    }
}