package com.internship.weatherapp;

/**
 * Created by apersin on 30-Mar-17.
 */

import android.app.Activity;
import android.content.Intent;


public class ThemeUtils {
    private static int cTheme;
    public final static int AppTheme = 0;
    public final static int Theme_AppCompat_DayNight = 1;
    public final static int ThemeOverlay_AppCompat_Dark = 2;

    public static void changeToTheme(Activity activity, int theme) {
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {

        switch (cTheme)
        {
            default:

            case AppTheme:
                activity.setTheme(R.style.AppTheme);
                break;

            case Theme_AppCompat_DayNight:
                activity.setTheme(R.style.Theme_AppCompat_DayNight);
                break;

            case ThemeOverlay_AppCompat_Dark:
                activity.setTheme(R.style.ThemeOverlay_AppCompat_Dark);
                break;
        }

    }

}