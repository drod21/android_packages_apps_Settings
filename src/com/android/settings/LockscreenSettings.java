package com.android.settings;
import com.android.settings.R;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

public class LockscreenSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String LOCKSCREEN_TARGETS = "lockscreen_targets";
    private static final String CENTER_LOCKSCREEN = "center_lockscreen";
    private ListPreference mLockscreenTarget;
    private CheckBoxPreference mCenteredLockscreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.lockscreen_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mLockscreenTarget = (ListPreference) prefSet.findPreference(LOCKSCREEN_TARGETS);
        int lockscreenValue = Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_TARGETS, 0);
        mLockscreenTarget.setValueIndex(lockscreenValue);
        mLockscreenTarget.setOnPreferenceChangeListener(this);

        mCenteredLockscreen = (CheckBoxPreference) prefSet.findPreference(CENTER_LOCKSCREEN);
        mCenteredLockscreen.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.CENTER_LOCKSCREEN, 0) == 1);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mCenteredLockscreen) {
            value = mCenteredLockscreen.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.CENTER_LOCKSCREEN, value ? 1 : 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mLockscreenTarget) {
            int lockscreenVal = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCKSCREEN_TARGETS, lockscreenVal);
            return true;
        }
        return false;
    }
}
