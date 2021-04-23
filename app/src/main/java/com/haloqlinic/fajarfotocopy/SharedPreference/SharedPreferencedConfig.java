package com.haloqlinic.fajarfotocopy.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencedConfig {

    public static final String PREFERENCE_FAJAR_FOTOCOPY = "prefFajarFotocopy";
    public static final String PREFERENCE_ID_USER = "prefIdUser";
    public static final String PREFERENCE_NAMA = "prefNama";
    public static final String PREFERENCE_USERNAME = "prefUsername";
    public static final String PREFERENCE_LEVEL = "prefLevel";
    public static final String PREFERENCE_ID_OUTLET = "prefIdOutlet";
    public static final String PREFERENCE_IMG = "prefImg";
    public static final String PREFERENCE_IS_LOGIN = "prefIsLogin";

    SharedPreferences preferences;
    SharedPreferences.Editor preferencesEditor;

    public SharedPreferencedConfig(Context context) {

        preferences = context.getSharedPreferences(PREFERENCE_FAJAR_FOTOCOPY, Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }

    public void savePrefString(String keyPref, String value){
        preferencesEditor.putString(keyPref, value);
        preferencesEditor.commit();
    }

    public void savePrefInt(String keyPref, int value){
        preferencesEditor.putInt(keyPref, value);
        preferencesEditor.commit();
    }

    public void savePrefBoolean(String keyPref, boolean value){
        preferencesEditor.putBoolean(keyPref, value);
        preferencesEditor.commit();
    }

    public String getPreferenceIdUser(){
        return preferences.getString(PREFERENCE_ID_USER, "");
    }

    public String getPreferenceNama(){
        return preferences.getString(PREFERENCE_NAMA, "");
    }

    public String getPreferenceUsername(){
        return preferences.getString(PREFERENCE_USERNAME, "");
    }

    public String getPreferenceLevel(){
        return preferences.getString(PREFERENCE_LEVEL, "");
    }

    public String getPreferenceIdOutlet(){
        return preferences.getString(PREFERENCE_ID_OUTLET, "");
    }

    public String getPreferenceImg(){
        return preferences.getString(PREFERENCE_IMG, "");
    }

    public Boolean getPreferenceIsLogin(){
        return preferences.getBoolean(PREFERENCE_IS_LOGIN, false);
    }
}
