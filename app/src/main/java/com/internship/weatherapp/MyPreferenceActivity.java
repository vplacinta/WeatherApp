package com.internship.weatherapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;


public class MyPreferenceActivity extends PreferenceActivity {

    public static final int RESULT_CODE_THEME_UPDATED = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefFragment())
                .commit();
    }
}
