package com.android.settings;
import com.android.settings.R;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

public class NavigationBarSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String PERSIST_MENU = "persist_menu";
    private CheckBoxPreference mPersistMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.navigation_bar_settings);
        PreferenceScreen prefSet = getPreferenceScreen();

        mPersistMenu = (CheckBoxPreference) prefSet.findPreference(PERSIST_MENU);
        mPersistMenu.setChecked(Settings.System.getInt(getContentResolver(),
            Settings.System.PERSIST_MENU, 0) == 1);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mPersistMenu) {
            value = mPersistMenu.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.PERSIST_MENU, value ? 1 : 0);
            return true;
        }
        return false;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}
