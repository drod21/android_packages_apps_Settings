package com.android.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;

import java.util.List;

public class RootzSettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "RootzBoatSettings";

    private static final String LAUNCHER_SETTING = "launcher_settings_coming_soon";
    private static final String LOCKSCREEN_SETTING = "lockscreen_settings_coming_soon";
    private static final String NAVIGATION_BAR_SETTING = "navigation_bar_settings_coming_soon";
    private static final String STATUS_BAR_SETTING = "status_bar_settings_coming_soon";

    private Preference mLauncherSetting;
    private Preference mLockscreenSetting;
    private Preference mNavigationBarSetting;
    private Preference mStatusBarSetting;
    private PreferenceGroup mRootzBoatSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.rootz_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mLauncherSetting = (Preference) prefSet.findPreference(LAUNCHER_SETTING);
        mLockscreenSetting = (Preference) prefSet.findPreference(LOCKSCREEN_SETTING);
        mNavigationBarSetting = (Preference) prefSet.findPreference(NAVIGATION_BAR_SETTING);
        mStatusBarSetting = (Preference) prefSet.findPreference(STATUS_BAR_SETTING);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mLauncherSetting) {
        } else if (preference == mLockscreenSetting) {
        } else if (preference == mNavigationBarSetting) {
        } else if (preference == mStatusBarSetting) {
            return false;
        }
        return true;
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();
        return true;
    }
}
