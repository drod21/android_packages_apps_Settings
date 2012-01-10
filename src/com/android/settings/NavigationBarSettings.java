package com.android.settings;
import com.android.settings.R;

import net.margaritov.preference.colorpicker.ColorPickerPreference;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

public class NavigationBarSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String SHOW_MENU_BUTTON = "show_menu_button";
    private static final String SHOW_SEARCH_BUTTON = "show_search_button";
    private static final String USE_ALT_ICONS = "use_alt_icons";
    private static final String NAVIGATION_BUTTON_COLOR = "navigation_button_color";
    private CheckBoxPreference mShowMenuButton;
    private CheckBoxPreference mShowSearchButton;
    private CheckBoxPreference mUseAltIcons;
    private ColorPickerPreference mNavButtonColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.navigation_bar_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mShowMenuButton = (CheckBoxPreference) prefSet.findPreference(SHOW_MENU_BUTTON);
        mShowMenuButton.setChecked(Settings.System.getInt(getContentResolver(),
            Settings.System.SHOW_MENU_BUTTON, 0) == 1);

        mShowSearchButton = (CheckBoxPreference) prefSet.findPreference(SHOW_SEARCH_BUTTON);
        mShowSearchButton.setChecked(Settings.System.getInt(getContentResolver(),
            Settings.System.SHOW_SEARCH_BUTTON, 0) == 1);

        mUseAltIcons = (CheckBoxPreference) prefSet.findPreference(USE_ALT_ICONS);
        mUseAltIcons.setChecked(Settings.System.getInt(getContentResolver(),
            Settings.System.USE_ALT_ICONS, 0) == 1);

        mNavButtonColor = (ColorPickerPreference) prefSet.findPreference(NAVIGATION_BUTTON_COLOR);
        mNavButtonColor.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mShowMenuButton) {
            value = mShowMenuButton.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.SHOW_MENU_BUTTON, value ? 1 : 0);
            return true;
        } else if (preference == mShowSearchButton) {
            value = mShowSearchButton.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.SHOW_SEARCH_BUTTON, value ? 1 : 0);
            return true;
        } else if (preference == mUseAltIcons) {
            value = mUseAltIcons.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.USE_ALT_ICONS, value ? 1 : 0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mNavButtonColor) {
            String hex = ColorPickerPreference.convertToARGB(Integer.valueOf(String
                .valueOf(newValue)));
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.NAVIGATION_BUTTON_COLOR, intHex);
            return true;
        }
        return false;
    }
}
