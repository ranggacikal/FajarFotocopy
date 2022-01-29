package com.haloqlinic.fajarfotocopy.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencedConfig {

    public static final String PREFERENCE_FAJAR_FOTOCOPY = "prefFajarFotocopy";
    public static final String PREFERENCE_ID_USER = "prefIdUser";
    public static final String PREFERENCE_NAMA = "prefNama";
    public static final String PREFERENCE_NAMA_TOKO = "prefNamaToko";
    public static final String PREFERENCE_USERNAME = "prefUsername";
    public static final String PREFERENCE_LEVEL = "prefLevel";
    public static final String PREFERENCE_ID_OUTLET = "prefIdOutlet";
    public static final String PREFERENCE_ID_STATUS_PENJUALAN = "prefIdStatusPenjualan";
    public static final String PREFERENCE_IMG = "prefImg";
    public static final String PREFERENCE_TOKEN_FCM = "prefTokenFcm";
    public static final String PREFERENCE_IS_LOGIN = "prefIsLogin";
    public static final String PREFERENCE_LAST_ID_STATUS_PENGIRIMAN = "prefLatIdStatusPengiriman";
    public static final int PREFERENCE_STOCK_PACK_SISA = 0;

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

    public String getPreferenceNamaToko(){
        return preferences.getString(PREFERENCE_NAMA_TOKO, "");
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

    public String getPreferenceIdStatusPenjualan(){
        return preferences.getString(PREFERENCE_ID_STATUS_PENJUALAN, "");
    }

    public String getPreferenceImg(){
        return preferences.getString(PREFERENCE_IMG, "");
    }

    public String getPreferenceTokenFcm(){
        return preferences.getString(PREFERENCE_TOKEN_FCM, "");
    }

    public Boolean getPreferenceIsLogin(){
        return preferences.getBoolean(PREFERENCE_IS_LOGIN, false);
    }

    public String getPreferenceLastIdStatusPengiriman(){
        return preferences.getString(PREFERENCE_LAST_ID_STATUS_PENGIRIMAN, "");
    }

    public int getPreferenceStockPackSisa(){
        return preferences.getInt(String.valueOf(PREFERENCE_STOCK_PACK_SISA), 0);
    }
}
