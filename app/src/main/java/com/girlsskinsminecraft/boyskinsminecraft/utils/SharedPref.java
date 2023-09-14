package com.girlsskinsminecraft.boyskinsminecraft.utils;

import android.content.Context;

public class SharedPref {
    private Context context;
    private RemoteConfig remoteConfig = new RemoteConfig();

    public SharedPref(Context context) {
        this.context = context;
    }
    public RemoteConfig getRemoteConfig() {
        return this.remoteConfig;
    }

    public String getBannerUnitIdHigh() {
        return this.remoteConfig.getBannerUnitIdHigh();
    }

    public String getBannerUnitIdMid() {
        return this.remoteConfig.getBannerUnitIdMid();
    }

    public String getBannerUnitIdLow() {
        return this.remoteConfig.getBannerUnitIdLow();
    }

    public String getInterUnitIdHigh() {
        return this.remoteConfig.getInterUnitIdHigh();
    }

    public String getInterUnitIdMid() {
        return this.remoteConfig.getInterUnitIdMid();
    }

    public String getInterUnitIdLow() {
        return this.remoteConfig.getInterUnitIdLow();
    }

    public String getRewardUnitIdHigh() {
        return this.remoteConfig.getRewardUnitIdHigh();
    }

    public String getRewardUnitIdMid() {
        return this.remoteConfig.getRewardUnitIdMid();
    }

    public String getRewardUnitIdLow() {
        return this.remoteConfig.getRewardUnitIdLow();
    }
}
