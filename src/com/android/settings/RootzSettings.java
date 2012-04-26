package com.android.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import java.util.List;

public class RootzSettings extends SettingsPreferenceFragment {

    private static final String LAUNCHER_PREFS = "launcher_prefs";

    PreferenceScreen mLauncherPrefs;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.rootz_settings);

        mLauncherPrefs = (PreferenceScreen) findPreference(LAUNCHER_PREFS);
    }
}
