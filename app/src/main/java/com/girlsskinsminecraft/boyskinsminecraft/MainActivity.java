package com.girlsskinsminecraft.boyskinsminecraft;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.girlsskinsminecraft.boyskinsminecraft.fragment.BaseFragment;
import com.girlsskinsminecraft.boyskinsminecraft.fragment.IRewardAdded;
import com.girlsskinsminecraft.boyskinsminecraft.fragment.SkinDetailsFragment;
import com.girlsskinsminecraft.boyskinsminecraft.fragment.SkinsFragment;
import com.girlsskinsminecraft.boyskinsminecraft.utils.SharedPref;

import com.yodo1.mas.Yodo1Mas;
import com.yodo1.mas.appopenad.Yodo1MasAppOpenAd;
import com.yodo1.mas.appopenad.Yodo1MasAppOpenAdListener;
import com.yodo1.mas.banner.Yodo1MasBannerAdListener;
import com.yodo1.mas.banner.Yodo1MasBannerAdView;
import com.yodo1.mas.error.Yodo1MasError;
import com.yodo1.mas.interstitial.Yodo1MasInterstitialAd;
import com.yodo1.mas.interstitial.Yodo1MasInterstitialAdListener;
import com.yodo1.mas.reward.Yodo1MasRewardAd;
import com.yodo1.mas.reward.Yodo1MasRewardAdListener;


import io.github.inflationx.viewpump.ViewPumpContextWrapper;

//,InAppUpdateManager.InAppUpdateHandler
public class MainActivity extends AppCompatActivity implements IMainManager {
    private static InterstitialAd adMobInterstitialAd = null;
    private static int countBanner = 1;
    private static int countInterstitial = 1;
    private static int countReward = 1;
    //    public static FrameLayout frameLayout;
    private String TAG = "MainActivity";
    AdView adView;
    private IRewardAdded calledOnResume;
    private IRewardAdded fragmentCallback;
    private InterstitialAd interstitialAd;
    private SharedPref sharedPref;
    private RewardedAd videoAd;




    static int access$008() {
        int i = countReward;
        countReward = i + 1;
        return i;
    }

    static int access$408() {
        int i = countBanner;
        countBanner = i + 1;
        return i;
    }

    static int access$708() {
        int i = countInterstitial;
        countInterstitial = i + 1;
        return i;
    }

    private Yodo1MasBannerAdView bannerAdView;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        loadInterAd();

//         inAppUpdateManager = InAppUpdateManager.Builder(this, REQ_CODE_VERSION_UPDATE)
//                .resumeUpdates(true) // Resume the update, if the update was stalled. Default is true
//                .mode(Constants.UpdateMode.FLEXIBLE)
//                .snackBarMessage("An update has just been downloaded.")
//                .snackBarAction("RESTART")
//                .handler(this);

//        inAppUpdateManager.checkForAppUpdate();
//        Yodo1MasAdBuildConfig config = new Yodo1MasAdBuildConfig.Builder().enableUserPrivacyDialog(true).build();
//        Yodo1Mas.getInstance().setAdBuildConfig(config);

        Yodo1Mas.getInstance().setGDPR(true);
        Yodo1Mas.getInstance().setCOPPA(false);
        Yodo1Mas.getInstance().setCCPA(true);
        Yodo1Mas.getInstance().init(this, "SWvX28aTXM", new Yodo1Mas.InitListener() {
            @Override
            public void onMasInitSuccessful() {
            }

            @Override
            public void onMasInitFailed(@NonNull Yodo1MasError error) {
            }
        });
        bannerAdView = findViewById(R.id.yodo1_mas_banner);

        bannerAdView.loadAd();
        this.sharedPref = new SharedPref(this);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.clearFlags(67108864);
        }
        this.adView = new AdView(this);
        loadRewardedVideoAd();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        boolean isVIp = sharedPreferences.getBoolean("VIP", false);
        if (!isVIp) {


            Yodo1MasAppOpenAd appOpenAd = Yodo1MasAppOpenAd.getInstance();

            appOpenAd.loadAd(MainActivity.this);

            appOpenAd.setAdListener(new Yodo1MasAppOpenAdListener() {
                @Override
                public void onAppOpenAdLoaded(Yodo1MasAppOpenAd ad) {
                    // Code to be executed when an ad finishes loading.
                    ad.showAd(MainActivity.this);
                }

                @Override
                public void onAppOpenAdFailedToLoad(Yodo1MasAppOpenAd ad, @NonNull Yodo1MasError error) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAppOpenAdOpened(Yodo1MasAppOpenAd ad) {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAppOpenAdFailedToOpen(Yodo1MasAppOpenAd ad, @NonNull Yodo1MasError error) {
                    // Code to be executed when an ad open fails.
                }

                @Override
                public void onAppOpenAdClosed(Yodo1MasAppOpenAd ad) {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });


        }


//        frameLayout = (FrameLayout) findViewById(R.id.adView);

//        loadAd(this);
//        loadBanner();
        setScreen(SkinsFragment.createInstance(0));
    }




    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));
    }

    @Override
    public void addScreen(BaseFragment baseFragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager == null) {
            return;
        }
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, baseFragment).addToBackStack(null).commit();
    }

    @Override
    public void setScreen(BaseFragment baseFragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager == null) {
            return;
        }
        supportFragmentManager.popBackStack((String) null, 1);
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, baseFragment).commit();
    }

    @Override
    public void closeScreen() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager == null || supportFragmentManager.getBackStackEntryCount() == 0) {
            return;
        }
        supportFragmentManager.popBackStack();
    }

    @Override
    public void showDialog(DialogFragment dialogFragment) {
        dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
    }

    public void loadRewardedVideoAd() {
        String rewardUnitIdHigh;
        int i = countReward;
        if (i == 1) {
            rewardUnitIdHigh = this.sharedPref.getRewardUnitIdHigh();
        } else if (i == 2) {
            rewardUnitIdHigh = this.sharedPref.getRewardUnitIdMid();
        } else {
            rewardUnitIdHigh = i != 3 ? "" : this.sharedPref.getRewardUnitIdLow();
        }
        Yodo1MasRewardAd.getInstance().setAdListener(new Yodo1MasRewardAdListener() {

            @Override
            public void onRewardAdLoaded(Yodo1MasRewardAd ad) {


                int unused = MainActivity.countReward = 0;
                Log.d(MainActivity.this.TAG, "Reward Ad was loaded.");

            }


            @Override
            public void onRewardAdFailedToLoad(Yodo1MasRewardAd ad, @NonNull Yodo1MasError error) {

                ad.loadAd(MainActivity.this);

            }

            @Override
            public void onRewardAdOpened(Yodo1MasRewardAd ad) {

            }

            @Override
            public void onRewardAdFailedToOpen(Yodo1MasRewardAd ad, @NonNull Yodo1MasError error) {
                ad.loadAd(MainActivity.this);
            }

            @Override
            public void onRewardAdClosed(Yodo1MasRewardAd ad) {
                ad.loadAd(MainActivity.this);
            }

            @Override
            public void onRewardAdEarned(Yodo1MasRewardAd ad) {
                SkinDetailsFragment.getInstance().startAction();
            }
        });
    }

//        public void loadRewardedVideoAd() {
//        String rewardUnitIdHigh;
//        int i = countReward;
//        if (i == 1) {
//            rewardUnitIdHigh = this.sharedPref.getRewardUnitIdHigh();
//        } else if (i == 2) {
//            rewardUnitIdHigh = this.sharedPref.getRewardUnitIdMid();
//        } else {
//            rewardUnitIdHigh = i != 3 ? "" : this.sharedPref.getRewardUnitIdLow();
//        }
//        RewardedAd.load(this, rewardUnitIdHigh, new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
//            @Override
//            public void onAdFailedToLoad(LoadAdError loadAdError) {
//                MainActivity.access$008();
//                if (MainActivity.countReward <= 3) {
//                    MainActivity.this.loadRewardedVideoAd();
//                } else {
//                    int unused = MainActivity.countReward = 0;
//                }
//                Log.d(MainActivity.this.TAG, loadAdError.toString());
//                MainActivity.this.videoAd = null;
//            }
//
//            @Override
//            public void onAdLoaded(RewardedAd rewardedAd) {
//                MainActivity.this.videoAd = rewardedAd;
//                int unused = MainActivity.countReward = 0;
//                Log.d(MainActivity.this.TAG, "Reward Ad was loaded.");
//            }
//        });
//    }

    public void loadBanner() {
        String bannerUnitIdHigh;
        int i = countBanner;
        if (i == 1) {
            bannerUnitIdHigh = this.sharedPref.getBannerUnitIdHigh();
        } else if (i == 2) {
            bannerUnitIdHigh = this.sharedPref.getBannerUnitIdMid();
        } else {
            bannerUnitIdHigh = i != 3 ? "" : this.sharedPref.getBannerUnitIdLow();
        }
        bannerAdView.setAdListener(new Yodo1MasBannerAdListener() {
            @Override
            public void onBannerAdLoaded(Yodo1MasBannerAdView bannerAdView) {
// Code to be executed when an ad finishes loading.
                Log.i("BannerAd", "onAdLoaded: " + MainActivity.countBanner);
                int unused = MainActivity.countBanner = 0;

            }

            @Override
            public void onBannerAdFailedToLoad(Yodo1MasBannerAdView bannerAdView, @NonNull Yodo1MasError error) {
// Code to be executed when an ad request fails.
                MainActivity.access$408();
                if (MainActivity.countBanner <= 3) {
                    MainActivity.this.loadBanner();
                } else {
                    int unused = MainActivity.countBanner = 0;
                }
                Log.i("BannerAd", "onAdFailedToLoad: " + MainActivity.countBanner);
            }

            @Override
            public void onBannerAdOpened(Yodo1MasBannerAdView bannerAdView) {
// Code to be executed when an ad opens an overlay that covers the screen.
            }

            @Override
            public void onBannerAdFailedToOpen(Yodo1MasBannerAdView bannerAdView, @NonNull Yodo1MasError error) {
// Code to be executed when an ad open fails.
            }

            @Override
            public void onBannerAdClosed(Yodo1MasBannerAdView bannerAdView) {
// Code to be executed when the user is about to return to the app after tapping on an ad.
            }
        });
        bannerAdView.loadAd();
    }


//    public void loadBanner() {
//        String bannerUnitIdHigh;
//        int i = countBanner;
//        if (i == 1) {
//            bannerUnitIdHigh = this.sharedPref.getBannerUnitIdHigh();
//        } else if (i == 2) {
//            bannerUnitIdHigh = this.sharedPref.getBannerUnitIdMid();
//        } else {
//            bannerUnitIdHigh = i != 3 ? "" : this.sharedPref.getBannerUnitIdLow();
//        }
//        AdView adView = new AdView(this);
//        this.adView = adView;
//        adView.setAdUnitId(bannerUnitIdHigh);
//        this.adView.setAdListener(new AdListener() {
//            @Override
//            public void onAdFailedToLoad(LoadAdError loadAdError) {
//                MainActivity.access$408();
//                if (MainActivity.countBanner <= 3) {
//                    MainActivity.this.loadBanner();
//                } else {
//                    int unused = MainActivity.countBanner = 0;
//                }
//                Log.i("BannerAd", "onAdFailedToLoad: " + MainActivity.countBanner);
//            }
//
//            @Override
//            public void onAdLoaded() {
//                Log.i("BannerAd", "onAdLoaded: " + MainActivity.countBanner);
//                int unused = MainActivity.countBanner = 0;
//            }
//        });
//        frameLayout.removeAllViews();
//        frameLayout.addView(this.adView);
//        AdRequest build = new AdRequest.Builder().build();
//        AdSize adSize = getAdSize();
//        if (adSize != null) {
//            this.adView.setAdSize(adSize);
//            this.adView.loadAd(build);
//        }
//    }

//    public void loadAd(final MainActivity mainActivity) {
//        String interUnitIdHigh;
//        int i = countInterstitial;
//        if (i == 1) {
//            interUnitIdHigh = this.sharedPref.getInterUnitIdHigh();
//        } else if (i == 2) {
//            interUnitIdHigh = this.sharedPref.getInterUnitIdMid();
//        } else {
//            interUnitIdHigh = i != 3 ? "" : this.sharedPref.getInterUnitIdLow();
//        }
//        InterstitialAd.load(mainActivity, interUnitIdHigh, getAdRequest(this, false), new InterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(InterstitialAd interstitialAd) {
//                InterstitialAd unused = MainActivity.adMobInterstitialAd = interstitialAd;
//                int unused2 = MainActivity.countInterstitial = 0;
//                MainActivity.adMobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                    @Override
//                    public void onAdShowedFullScreenContent() {
//                    }
//
//                    @Override
//                    public void onAdDismissedFullScreenContent() {
//                        InterstitialAd unused3 = MainActivity.adMobInterstitialAd = null;
//                        MainActivity.this.loadAd(MainActivity.this);
//                    }
//                });
//            }
//
//            @Override
//            public void onAdFailedToLoad(LoadAdError loadAdError) {
//                Log.i(MainActivity.this.TAG, loadAdError.getMessage());
//                InterstitialAd unused = MainActivity.adMobInterstitialAd = null;
//                MainActivity.access$708();
//                if (MainActivity.countInterstitial > 3) {
//                    int unused2 = MainActivity.countInterstitial = 0;
//                } else {
//                    MainActivity.this.loadAd(mainActivity);
//                }
//                Log.i("Interstitial", "onAdFailedToLoad: " + MainActivity.countInterstitial);
//            }
//        });
//    }

    private AdSize getAdSize() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(getApplicationContext(), (int) (displayMetrics.widthPixels / displayMetrics.density));
    }

    public static AdRequest getAdRequest(Context context, boolean z) {
        return new AdRequest.Builder().build();
    }

//    public void showInterstitialAd() {
//        InterstitialAd interstitialAd = adMobInterstitialAd;
//        if (interstitialAd != null) {
//            interstitialAd.show(this);
//            LocalStorage.setOpensWithoutAd(this, 0);
//            return;
//        }
//        Log.d("ad_count", "The interstitial ad wasn't ready yet.");
//    }

    public void showVideoAd() {
        boolean isLoaded = Yodo1MasRewardAd.getInstance().isLoaded();
        if (isLoaded) Yodo1MasRewardAd.getInstance().showAd(MainActivity.this, "Your Placement");
//            MainActivity.this.loadRewardedVideoAd();
//            SkinDetailsFragment.getInstance().startAction();
//        }

    }
//
//    @Override
//    public void onInAppUpdateError(int code, Throwable error) {
//
//    }
//
//    @Override
//    public void onInAppUpdateStatus(InAppUpdateStatus status) {
//        if (status.isDownloaded()) {
//
//            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
//
//            Snackbar snackbar = Snackbar.make(rootView,
//                    "An update has just been downloaded.",
//                    Snackbar.LENGTH_INDEFINITE);
//
//            snackbar.setAction("RESTART", view -> {
//
//                // Triggers the completion of the update of the app for the flexible flow.
//                inAppUpdateManager.completeUpdate();
//
//            });
//
//            snackbar.show();
//        }
//
//    }
////    public void showVideoAdlist() {
//        boolean isLoaded = Yodo1MasRewardAd.getInstance().isLoaded();
//        if (isLoaded) {
//            Yodo1MasRewardAd.getInstance().showAd(MainActivity.this, "Your Placement");
//            MainActivity.this.loadRewardedVideoAd();
//
//        }
//
//    }



    public void loadInterAd() {
        Yodo1MasInterstitialAd.getInstance().setAdListener(new Yodo1MasInterstitialAdListener() {

            @Override
            public void onInterstitialAdLoaded(Yodo1MasInterstitialAd ad) {



            }

            @Override
            public void onInterstitialAdFailedToLoad(Yodo1MasInterstitialAd ad, @NonNull Yodo1MasError error) {

                ad.loadAd(MainActivity.this);
            }

            @Override
            public void onInterstitialAdOpened(Yodo1MasInterstitialAd ad) {

            }

            @Override
            public void onInterstitialAdFailedToOpen(Yodo1MasInterstitialAd ad, @NonNull Yodo1MasError error) {

            }

            @Override
            public void onInterstitialAdClosed(Yodo1MasInterstitialAd ad) {

            }
        });



    }
    public void showInterAd() {
        boolean isLoaded = Yodo1MasInterstitialAd.getInstance().isLoaded();
        if(isLoaded) {
            Yodo1MasInterstitialAd.getInstance().showAd(MainActivity.this, "Your Placement");
        }

    }

}