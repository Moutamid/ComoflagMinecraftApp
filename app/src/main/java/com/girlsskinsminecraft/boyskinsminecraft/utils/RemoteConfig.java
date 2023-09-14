package com.girlsskinsminecraft.boyskinsminecraft.utils;

import android.app.Application;

import com.girlsskinsminecraft.boyskinsminecraft.R;


public class RemoteConfig extends Application {
    private static final String ID_ADS_BANNER = "ca-app-pub-3940256099942544/6300978111";
    private static final String ID_ADS_INTER = "ca-app-pub-3940256099942544/1033173712";
    private static final String ID_ADS_REWARD = "ca-app-pub-3940256099942544/5224354917";
    public RemoteConfig() {

    }


    public String getBannerUnitIdHigh() {
        return ID_ADS_BANNER;
    }

    public String getBannerUnitIdMid() {
        return ID_ADS_BANNER;
    }

    public String getBannerUnitIdLow() {
        return ID_ADS_BANNER;

    }

    public String getInterUnitIdHigh() {
        return ID_ADS_INTER;
    }

    public String getInterUnitIdMid() {
        return ID_ADS_INTER;
    }

    public String getInterUnitIdLow() {
        return ID_ADS_INTER;
    }

    public String getRewardUnitIdHigh() {
        return ID_ADS_REWARD;
    }

    public String getRewardUnitIdMid() {
        return ID_ADS_REWARD;
    }

    public String getRewardUnitIdLow() {
        return ID_ADS_REWARD;
    }
}
