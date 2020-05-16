package com.androidvoyage.bmc.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.androidvoyage.bmc.BCApplication;

/**
 * Created by Adveti Creatives on 26-07-2017.
 */

public class BCSharedPreference {

    public static final String PREF_USER_DETAILS = "USER_PROFILE";
    private static BCSharedPreference instance = null;
    private SharedPreferences sharedPreferences;

    private BCSharedPreference() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BCApplication.getAppContext());
    }

    public static BCSharedPreference getInstance() {
        if (instance == null) {
            instance = new BCSharedPreference();
        }
        return instance;
    }

    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BCApplication.getAppContext());
        }
        return sharedPreferences;
    }
}
