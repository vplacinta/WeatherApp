package com.internship.weatherapp.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.internship.weatherapp.R;

public class SettingsFragment extends PreferenceFragment {

    public static final int RESULT_CODE_THEME_UPDATED = 1;
    private static final String THEME_PREFERENCE = "theme_pref";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.fragmented_preferences);

        findPreference(THEME_PREFERENCE).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                getActivity().setResult(RESULT_CODE_THEME_UPDATED);
                getActivity().finish();
                return true;
            }
        });

    }
}

