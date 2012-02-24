package com.android.settings;
import com.android.settings.R;

import net.margaritov.preference.colorpicker.ColorPickerPreference;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

public class StatusBarSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String BATTERY_PERCENTAGES = "battery_percentages";
    private static final String HIDE_BATTERY = "hide_battery";
    private static final String BATTERY_BAR = "battery_bar";
    private static final String BATTERY_BAR_STYLE = "battery_bar_style";
    private static final String BATTERY_BAR_COLOR = "battery_bar_color";
    private static final String BATTERY_BAR_WIDTH = "battery_bar_thickness";
    private static final String BATTERY_BAR_ANIMATE = "battery_bar_animate";
    private static final String PREF_CLOCK_DISPLAY_STYLE = "clock_am_pm";
    private static final String PREF_CLOCK_STYLE = "clock_style";

    private CheckBoxPreference mBatteryPercentages;
    private CheckBoxPreference mHideBattery;
    private ListPreference mBatteryBar;
    private ListPreference mBatteryBarStyle;
    private ListPreference mBatteryBarThickness;
    private ColorPickerPreference mBatteryBarColor;
    private CheckBoxPreference mBatteryBarChargingAnim;
    private ListPreference mAmPmStyle;
    private ListPreference mClockStyle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.status_bar_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mBatteryPercentages = (CheckBoxPreference) prefSet.findPreference(BATTERY_PERCENTAGES);
        mBatteryPercentages.setChecked(Settings.System.getInt(getContentResolver(),
            Settings.System.BATTERY_PERCENTAGES, 0) == 1);
        mHideBattery = (CheckBoxPreference) prefSet.findPreference(HIDE_BATTERY);
        mHideBattery.setChecked(Settings.System.getInt(getContentResolver(),
            Settings.System.HIDE_BATTERY, 0) == 1);
        mBatteryBar = (ListPreference) prefSet.findPreference(BATTERY_BAR);
        mBatteryBar.setOnPreferenceChangeListener(this);
        mBatteryBar.setValue((Settings.System.getInt(getContentResolver(),
            Settings.System.STATUSBAR_BATTERY_BAR, 0)) + "");
        mBatteryBarStyle = (ListPreference) prefSet.findPreference(BATTERY_BAR_STYLE);
        mBatteryBarStyle.setOnPreferenceChangeListener(this);
        mBatteryBarStyle.setValue((Settings.System.getInt(getContentResolver(),
            Settings.System.STATUSBAR_BATTERY_BAR_STYLE, 0)) + "");
        mBatteryBarColor = (ColorPickerPreference) prefSet.findPreference(BATTERY_BAR_COLOR);
        mBatteryBarColor.setOnPreferenceChangeListener(this);
        mBatteryBarChargingAnim = (CheckBoxPreference) prefSet.findPreference(BATTERY_BAR_ANIMATE);
        mBatteryBarChargingAnim.setChecked(Settings.System.getInt(getContentResolver(),
            Settings.System.STATUSBAR_BATTERY_BAR_ANIMATE, 0) == 1);
        mBatteryBarThickness = (ListPreference) prefSet.findPreference(BATTERY_BAR_WIDTH);
        mBatteryBarThickness.setOnPreferenceChangeListener(this);
        mBatteryBarThickness.setValue((Settings.System.getInt(getContentResolver(), Settings.System.STATUSBAR_BATTERY_BAR_THICKNESS, 1)) + "");
        mClockStyle = (ListPreference) prefSet.findPreference(PREF_CLOCK_STYLE);
        mAmPmStyle = (ListPreference) prefSet.findPreference(PREF_CLOCK_DISPLAY_STYLE);

        int styleValue = Settings.System.getInt(getContentResolver(),
                Settings.System.STATUS_BAR_AM_PM, 2);
        mAmPmStyle.setValueIndex(styleValue);
        mAmPmStyle.setOnPreferenceChangeListener(this);

        int clockVal = Settings.System.getInt(getContentResolver(),
                Settings.System.STATUS_BAR_CLOCK, 1);
        mClockStyle.setValueIndex(clockVal);
        mClockStyle.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mBatteryPercentages) {
            value = mBatteryPercentages.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.BATTERY_PERCENTAGES, value ? 1 : 0);
            return true;
        } else if (preference == mHideBattery) {
            value = mHideBattery.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.HIDE_BATTERY, value ? 1 : 0);
            return true;
        } else if (preference == mBatteryBarChargingAnim) {
            value = mBatteryBarChargingAnim.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.STATUSBAR_BATTERY_BAR_ANIMATE, value ? 1 : 0);
            return true;
        }
        return false;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mBatteryBarColor) {
            String hex = ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue)));
            preference.setSummary(hex);

            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(getContentResolver(),
                Settings.System.STATUSBAR_BATTERY_BAR_COLOR, intHex);
            return true;
        } else if (preference == mBatteryBar) {
            int val = Integer.parseInt((String) newValue);
            Settings.System.putInt(getContentResolver(),
                Settings.System.STATUSBAR_BATTERY_BAR, val);
            return true;
        } else if (preference == mBatteryBarStyle) {
            int val = Integer.parseInt((String) newValue);
            Settings.System.putInt(getContentResolver(),
                Settings.System.STATUSBAR_BATTERY_BAR_STYLE, val);
            return true;
        } else if (preference == mBatteryBarThickness) {
            int val = Integer.parseInt((String) newValue);
            Settings.System.putInt(getContentResolver(),
                Settings.System.STATUSBAR_BATTERY_BAR_THICKNESS, val);
            return true;
        } else if (preference == mAmPmStyle) {
            int statusBarAmPm = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(),
                Settings.System.STATUS_BAR_AM_PM, statusBarAmPm);
            return true;
        } else if (preference == mClockStyle) {
            int val = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(),
                Settings.System.STATUS_BAR_CLOCK, val);
            return true;
        }
        return false;
    }
}
