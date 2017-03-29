package com.internship.weatherapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.internship.weatherapp.R;

/**
 * Created by apersin on 29-Mar-17.
 */

public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_activity);
    }
}