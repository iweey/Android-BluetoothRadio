/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devin.BluetoothRadio;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference;
import android.util.Log;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.preference.SwitchPreference;
import android.preference.ListPreference;
//import com.sileria.android.view.SeekBarPreference;

public class PreferencesFromXml extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {

    private SharedPreferences prefs;
    //public static SwitchPreference mbeep_preference;
    public static SwitchPreference mloudness_preference;
    public static ListPreference mlist_preference_eq;

    public static SeekBarPreference mbalance_preference;
    public static boolean mbalance_preferenceSendFlag;
    public static int mbalance_preferenceIndex = 0;
    public static boolean seekBarSwithFlag = true;
    public static SeekBarPreference mfader_preference;
    public static int mfader_preferenceIndex = 0;

    private SeekBarPreferenceLight light_preference;

    //public static boolean beepFlag = false;
    public static boolean loudnessFlag = false;
    public static boolean controlFlag = false;
    public static int mlist_preference_eqIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        /*mbeep_preference = (SwitchPreference) findPreference("beep_preference");
        mbeep_preference.setOnPreferenceClickListener(this);
        mbeep_preference.setOnPreferenceChangeListener(this);*/

        controlFlag = true;

        mloudness_preference = (SwitchPreference) findPreference("loudness_preference");
        mloudness_preference.setOnPreferenceClickListener(this);
        mloudness_preference.setOnPreferenceChangeListener(this);

        mlist_preference_eq = (ListPreference) findPreference("list_preference_eq");
        mlist_preference_eq.setOnPreferenceChangeListener(this);
        if(mlist_preference_eqIndex == 0)
            mlist_preference_eq.setSummary("off");
        else if(mlist_preference_eqIndex == 1)
            mlist_preference_eq.setSummary("Flat");
        else if(mlist_preference_eqIndex == 2)
            mlist_preference_eq.setSummary("Classic");
        else if(mlist_preference_eqIndex == 3)
            mlist_preference_eq.setSummary("Rock");
        else if(mlist_preference_eqIndex == 4)
            mlist_preference_eq.setSummary("Pop");

        mbalance_preference = (SeekBarPreference)findPreference("balance_preference");
        mbalance_preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.e("gallery", "mbalance_preference onPreferenceClick");
                mbalance_preferenceSendFlag = false;
                return false;
            }
        });
        mbalance_preference.setMax(14);
        mbalance_preference.setProgress(7);

        //gallery--temporary
        //if(mbalance_preferenceIndex != 0) {
            mbalance_preference.setProgress(mbalance_preferenceIndex);
        //}
        if(mbalance_preferenceIndex == 7) {
            mbalance_preference.setSummary("00");
        }
        else if(mbalance_preferenceIndex > 7) {
            mbalance_preference.setSummary("R0" + (mbalance_preferenceIndex-7));
        }
        else if(mbalance_preferenceIndex < 7) {
            mbalance_preference.setSummary("L0" + (7-mbalance_preferenceIndex));
        }

        mbalance_preference.setOnPreferenceChangeListener(this);

        mfader_preference = (SeekBarPreference)findPreference("fader_preference");
        mfader_preference.setMax(14);
        mbalance_preference.setProgress(7);
        //gallery--temporary
        //if(mfader_preferenceIndex != 0) {
            mfader_preference.setProgress(mfader_preferenceIndex);
        //}
        if(mfader_preferenceIndex == 7) {
            mfader_preference.setSummary("00");
        }
        else if(mfader_preferenceIndex > 7) {
            mfader_preference.setSummary("F0" + (mfader_preferenceIndex-7));
        }
        else if(mfader_preferenceIndex < 7) {
            mfader_preference.setSummary("R0" + (7-mfader_preferenceIndex));
        }
        mfader_preference.setOnPreferenceChangeListener(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        /*if(beepFlag) {
            mbeep_preference.setChecked(true);
        }
        else {
            mbeep_preference.setChecked(false);
        }*/

        if(loudnessFlag) {
            mloudness_preference.setChecked(true);
        }
        else {
            mloudness_preference.setChecked(false);
        }

        light_preference = (SeekBarPreferenceLight) findPreference("light_preference");
        light_preference.setProgress((int) (android.provider.Settings.System.getInt( Activity02.galleryActivity.getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS, 255) ));
        light_preference.setSummary("Back Light: " + light_preference.getProgress());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // TODO Auto-generated method stub
        //Log.e("SystemSetting", "preference is changed");
        Log.e("gallery", preference.getKey());

        if(preference.getKey().equals("beep_preference")) {
            Log.e("gallery", prefs.getBoolean("beep_preference", false) + "");
            Log.e("gallery", "new: " + newValue.toString());
            if(newValue.toString().trim().equalsIgnoreCase("true")) {
                //FF 77 0E 05 01 00 00 00 0D 0A
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x05, (byte)0x01, (byte)0x00
                        , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
            else if(newValue.toString().trim().equalsIgnoreCase("false")) {
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x05, (byte)0x00, (byte)0x00
                        , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
        }
        else if(preference.getKey().equals("loudness_preference")) {
            Log.e("gallery", prefs.getBoolean("loudness_preference", false) + "");
            Log.e("gallery", "new: " + newValue.toString());
            if(newValue.toString().trim().equalsIgnoreCase("true")) {
                //FF 77 0E 06 01 00 00 00 0D 0A
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x06, (byte)0x01, (byte)0x00
                        , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
            else if(newValue.toString().trim().equalsIgnoreCase("false")) {
                //FF 77 0E 06 00 00 00 00 0D 0A
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x06, (byte)0x00, (byte)0x00
                        , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
        }
        else if(preference.getKey().equals("list_preference_eq")) {
            Log.e("gallery", prefs.getString("list_preference_eq", "NULL"));
            Log.e("gallery", "new: " + newValue.toString());
            mlist_preference_eq.setSummary(newValue.toString());
            //FF 77 0E 00 00 00 00 00 0D 0A
            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x00, (byte)0x00, (byte)0x00
                    , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
            if(newValue.toString().trim().equalsIgnoreCase("off")) {
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
            else if(newValue.toString().trim().equalsIgnoreCase("Flat")) {
                //FF 77 8B 00 01 00 00 00 0D 0A
                blueOrder[4] = (byte)0x01;
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
            else if(newValue.toString().trim().equalsIgnoreCase("Classic")) {
                blueOrder[4] = (byte)0x02;
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
            else if(newValue.toString().trim().equalsIgnoreCase("Rock")) {
                blueOrder[4] = (byte)0x03;
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
            else if(newValue.toString().trim().equalsIgnoreCase("Pop")) {
                blueOrder[4] = (byte)0x04;
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
        }
        else if(preference.getKey().equals("balance_preference")) {
            seekBarSwithFlag = true;
            //Log.e("gallery", "balance_preference onPreferenceChange === " + newValue.toString());
            //mbalance_preference.setSummary(" = " + newValue.toString());
            //FF 77 0E 03 XX 00 00 00 0D 0A
            //int value = Integer.parseInt(newValue.toString().trim());
            if(mbalance_preferenceSendFlag) {
                byte valueByte = Byte.parseByte(newValue.toString().trim());
                byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x03, valueByte, (byte)0x00
                        , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
                BluetoothDemo.sendBlueOrder(blueOrder);
            }
        }
        else if(preference.getKey().equals("fader_preference")) {
            seekBarSwithFlag = false;
            //Log.e("gallery", "fader_preference onPreferenceChange === " + newValue.toString());
            //mfader_preference.setSummary(" = " + newValue.toString());
            //FF 77 0E 04 XX 00 00 00 0D 0A
            byte valueByte = Byte.parseByte(newValue.toString().trim());
            byte[] blueOrder = {(byte)0xFF, (byte)0x77, (byte)0x0E, (byte)0x04, valueByte, (byte)0x00
                    , (byte)0x00, (byte)0x00, (byte)0x0D, (byte)0x0A};
            BluetoothDemo.sendBlueOrder(blueOrder);
        }

        return true;
    }
    @Override
    public boolean onPreferenceClick(Preference preference) {
        // TODO Auto-generated method stub
        //Log.v("SystemSetting", "preference is clicked");
        //Log.e("gallery", "click: " + preference.getKey());
        //Log.e("gallery", "click: " + prefs.getBoolean("beep_preference", true));

        return false;
    }

    public void onPause() {
        super.onPause();
        controlFlag = false;
    }
}
