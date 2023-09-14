package com.girlsskinsminecraft.boyskinsminecraft.utils;


public class BaseURL {
    public static final String HTTPS_skin = "https://namazvakitleri.io/camouflage/skin/";
    public static final String HTTPS_thumbnail = "https://namazvakitleri.io/camouflage/thumbnail/";

    public static String createThumbnailPath(String str) {
        return String.format("%s%s.png", HTTPS_thumbnail, str);
    }

    public static String createSkinPath(String str) {
        return String.format("%s%s.png", HTTPS_skin, str);
    }
}
