package com.internship.weatherapp;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class PrefFragment extends PreferenceFragment {

    public static final int RESULT_CODE_THEME_UPDATED = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.fragmented_preferences);

        findPreference("theme_pref").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                getActivity().setResult(RESULT_CODE_THEME_UPDATED);
                getActivity().finish();
                return true;
            }
        });

    }
}

