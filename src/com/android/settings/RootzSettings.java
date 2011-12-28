package com.android.settings;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ActivityInfo;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.content.pm.PackageManager;

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

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ActivityInfo a = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY).activityInfo;
        if (a != null && a.name.equals("com.android.launcher2.Launcher") && (a.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ){
        if ( findPreference(LAUNCHER_PREFS) == null){
                findPreference(LAUNCHER_PREFS).setEnabled(true);
            }
        } else {
            if ( findPreference(LAUNCHER_PREFS) != null){
                findPreference(LAUNCHER_PREFS).setEnabled(false);
            }
        }
    }
}
