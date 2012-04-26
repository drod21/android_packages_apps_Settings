package com.android.settings;
import com.android.settings.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import android.text.Spannable;
import android.widget.EditText;

public class LockscreenSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String LOCKSCREEN_TARGETS = "lockscreen_targets";
    private static final String CENTER_LOCKSCREEN = "center_lockscreen";
    private static final String LOCKSCREEN_ALWAYS_BATTERY = "lockscreen_always_battery";
    private static final String CUSTOM_CARRIER_LABEL = "custom_carrier_label";
    private ListPreference mLockscreenTarget;
    private CheckBoxPreference mCenteredLockscreen;
    private CheckBoxPreference mAlwaysShowBattInfo;
    private Preference mCustomLabel;
    private String mCustomLabelText = null;

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

        mAlwaysShowBattInfo = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_ALWAYS_BATTERY);
        mAlwaysShowBattInfo.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_ALWAYS_BATTERY, 0) == 1);

        mCustomLabel = prefSet.findPreference(CUSTOM_CARRIER_LABEL);
        updateCustomLabelTextSummary();
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mCenteredLockscreen) {
            value = mCenteredLockscreen.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.CENTER_LOCKSCREEN, value ? 1 : 0);
            return true;
        } else if (preference == mAlwaysShowBattInfo) {
            value = mAlwaysShowBattInfo.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.LOCKSCREEN_ALWAYS_BATTERY, value ? 1 : 0);
            return true;
        } else if (preference == mCustomLabel) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Custom carrier label");
            alert.setMessage("Please enter a new one");
            final EditText input = new EditText(getActivity());
            input.setText(mCustomLabelText != null ? mCustomLabelText : "");
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = ((Spannable) input.getText()).toString();
                        Settings.System.putString(getActivity().getContentResolver(),
                                Settings.System.CUSTOM_CARRIER_LABEL, value);
                        updateCustomLabelTextSummary();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
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

    private void updateCustomLabelTextSummary() {
        mCustomLabelText = Settings.System.getString(getActivity().getContentResolver(),
                Settings.System.CUSTOM_CARRIER_LABEL);
        if (mCustomLabelText == null){
            mCustomLabel.setSummary("Custom label not set");
        } else {
            mCustomLabel.setSummary(mCustomLabelText);
        }
    } 
}
