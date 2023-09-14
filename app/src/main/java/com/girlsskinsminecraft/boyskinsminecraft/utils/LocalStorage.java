package com.girlsskinsminecraft.boyskinsminecraft.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class LocalStorage {
    private static final String KEY_OPENS_WITHOUT_ADD = "opens_without_ad";
    private static final String PREFERENCES_FILE = "preferences_file";

    public static void setOpensWithoutAd(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCES_FILE, 0).edit();
        edit.putInt(KEY_OPENS_WITHOUT_ADD, i);
        edit.apply();
    }

    public static int getOpensWithoutAd(Context context) {
        return context.getSharedPreferences(PREFERENCES_FILE, 0).getInt(KEY_OPENS_WITHOUT_ADD, 0);
    }
}
