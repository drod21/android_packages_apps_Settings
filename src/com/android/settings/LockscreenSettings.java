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
    private static final String VOLUME_WAKE = "volume_wake";
    private static final String CUSTOM_CARRIER_LABEL = "custom_carrier_label";
  //  private static final String CRT_ON_ANIMATION = "crt_on_animation";
    private static final String CRT_OFF_ANIMATION = "crt_off_animation";
    private ListPreference mLockscreenTarget;
    private CheckBoxPreference mCenteredLockscreen;
    private CheckBoxPreference mVolumeWake;
   // private CheckBoxPreference mCrtOnAnimation;
    private CheckBoxPreference mCrtOffAnimation;
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

        mVolumeWake = (CheckBoxPreference) prefSet.findPreference(VOLUME_WAKE);
        mVolumeWake.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.VOLUME_WAKE, 0) == 1);

      //  mCrtOnAnimation = (CheckBoxPreference) prefSet.findPreference(CRT_ON_ANIMATION);
      //  mCrtOnAnimation.setChecked(Settings.System.getInt(getContentResolver(),
      //          Settings.System.CRT_ON_ANIMATION, 0) == 1);

        mCrtOffAnimation = (CheckBoxPreference) prefSet.findPreference(CRT_OFF_ANIMATION);
        mCrtOffAnimation.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.CRT_OFF_ANIMATION, 1) == 1);

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
        } else if (preference == mVolumeWake) {
            value = mVolumeWake.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.VOLUME_WAKE, value ? 1 : 0);
            return true;
       // } else if (preference == mCrtOnAnimation) {
       //     value = mCrtOnAnimation.isChecked();
       //     Settings.System.putInt(getContentResolver(),
       //         Settings.System.CRT_ON_ANIMATION, value ? 1 : 0);
       //     return true;
        } else if (preference == mCrtOffAnimation) {
            value = mCrtOffAnimation.isChecked();
            Settings.System.putInt(getContentResolver(),
                Settings.System.CRT_OFF_ANIMATION, value ? 1 : 0);
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
