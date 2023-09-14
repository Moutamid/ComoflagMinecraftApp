package com.girlsskinsminecraft.boyskinsminecraft.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;


public class FavoritesManager {
    private static final String KEY_FAVORITES = "favorites";
    private static final String STORAGE_NAME = "FavoritesLocalStorage";
    private static FavoritesManager instance;
    private Set<String> favorites;

    public static FavoritesManager getInstance() {
        if (instance == null) {
            instance = new FavoritesManager();
        }
        return instance;
    }

    public boolean isFavorite(Context context, String str) {
        initData(context);
        for (String str2 : this.favorites) {
            if (str2.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public void addToFavorite(Context context, String str) {
        initData(context);
        if (isFavorite(context, str)) {
            return;
        }
        this.favorites.add(str);
        saveToStorage(context, this.favorites);
    }

    public void removeFavorite(Context context, String str) {
        initData(context);
        if (isFavorite(context, str)) {
            this.favorites.remove(str);
            saveToStorage(context, this.favorites);
        }
    }

    private void initData(Context context) {
        if (this.favorites == null) {
            this.favorites = readFromStorage(context);
        }
    }

    private static Set<String> readFromStorage(Context context) {
        Set<String> stringSet = context.getSharedPreferences(STORAGE_NAME, 0).getStringSet(KEY_FAVORITES, new HashSet());
        return stringSet == null ? new HashSet() : new HashSet(stringSet);
    }

    private static void saveToStorage(Context context, Set<String> set) {
        SharedPreferences.Editor edit = context.getSharedPreferences(STORAGE_NAME, 0).edit();
        edit.putStringSet(KEY_FAVORITES, set);
        edit.apply();
    }
}
