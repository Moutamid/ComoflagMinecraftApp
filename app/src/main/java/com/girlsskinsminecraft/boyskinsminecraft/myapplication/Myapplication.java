package com.girlsskinsminecraft.boyskinsminecraft.myapplication;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.girlsskinsminecraft.boyskinsminecraft.R;


public class Myapplication extends Application {
    private static Context mContext;
    private static Myapplication mInstance;

    static  void lambda$onCreate$0(InitializationStatus initializationStatus) {
    }

    @Override 
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this;
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override 
            public final void onInitializationComplete(InitializationStatus initializationStatus) {
                Myapplication.lambda$onCreate$0(initializationStatus);
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }

    public static Myapplication getInstance() {
        return mInstance;
    }
}
