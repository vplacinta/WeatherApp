package com.internship.weatherapp;

import android.util.Log;

public class Logger {

    private static boolean canLog = false;

    public Logger(boolean canLog){
        this.canLog = canLog;
    }

    public static void logString(Object s, String logdata){
        String TAG = s.getClass().getSimpleName();
        if(canLog) {
            Log.e(TAG, logdata);
        }
    }
}