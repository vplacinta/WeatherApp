package com.internship.weatherapp.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.internship.weatherapp.fragments.SettingsFragment;

public class SettingsActivity extends PreferenceActivity {

    public static final int RESULT_CODE_THEME_UPDATED = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
