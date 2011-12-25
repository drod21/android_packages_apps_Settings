package com.android.settings;
import com.android.settings.R;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

public class StatusBarSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String BATTERY_PERCENTAGES = "battery_percentages";
    private CheckBoxPreference mBatteryPercentages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.status_bar_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mBatteryPercentages = (CheckBoxPreference) prefSet.findPreference(BATTERY_PERCENTAGES);
        mBatteryPercentages.setChecked(Settings.System.getInt(getContentResolver(),
            Settings.System.BATTERY_PERCENTAGES, 0) == 1);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mBatteryPercentages) {
            value = mBatteryPercentages.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.BATTERY_PERCENTAGES, value ? 1 : 0);
            return true;
        }
        return false;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}
