package com.jaeger.reading.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.jaeger.reading.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
