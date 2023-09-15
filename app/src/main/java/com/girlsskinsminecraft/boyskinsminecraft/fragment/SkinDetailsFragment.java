package com.girlsskinsminecraft.boyskinsminecraft.fragment;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.yodo1.mas.utils.Yodo1MasNativeUtil.dp2px;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.girlsskinsminecraft.boyskinsminecraft.MainActivity;
import com.girlsskinsminecraft.boyskinsminecraft.R;
import com.girlsskinsminecraft.boyskinsminecraft.dialogs.ConfirmDialog;
import com.girlsskinsminecraft.boyskinsminecraft.dialogs.SavedToGalleryDialog;
import com.girlsskinsminecraft.boyskinsminecraft.dialogs.SavedToMinecraftDialog;
import com.girlsskinsminecraft.boyskinsminecraft.model.Skin;
import com.girlsskinsminecraft.boyskinsminecraft.myapplication.Myapplication;
import com.girlsskinsminecraft.boyskinsminecraft.utils.BaseURL;
import com.girlsskinsminecraft.boyskinsminecraft.utils.ConstantFunctions;
import com.girlsskinsminecraft.boyskinsminecraft.utils.FavoritesManager;
import com.girlsskinsminecraft.boyskinsminecraft.utils.Helpers;
import com.girlsskinsminecraft.boyskinsminecraft.utils.LocalStorage;
import com.yodo1.mas.error.Yodo1MasError;
import com.yodo1.mas.interstitial.Yodo1MasInterstitialAd;
import com.yodo1.mas.interstitial.Yodo1MasInterstitialAdListener;
import com.yodo1.mas.nativeads.Yodo1MasNativeAdView;
import com.yodo1.mas.reward.Yodo1MasRewardAd;
import com.yodo1.mas.reward.Yodo1MasRewardAdListener;

import java.io.File;
import java.util.Objects;


public class SkinDetailsFragment extends BaseFragment {
    private static final String EXTRA_NAME = "extra_name";
    private static final String EXTRA_POSITION = "extra_position";
    private static final int WRITE_PERMISSION_CODE = 10256;
    private static final int WRITE_PERMISSION_CODE13 = 10456;
    private static SkinDetailsFragment instance;
    private ActionType actionType;
    private boolean alreadyLoaded;
    private final View.OnClickListener onFavoriteClicked = new View.OnClickListener() {
        @Override
        public final void onClick(View view) {
            SkinDetailsFragment.this.m41x2e4c118e(view);
        }
    };
    private Skin skin;
    private View view;
    private Yodo1MasNativeAdView nativeAdView;

    private enum ActionType {
        Gallery,
        Application
    }

    public static SkinDetailsFragment createInstance(Skin skin) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_POSITION, skin.getNumber());
        bundle.putString(EXTRA_NAME, skin.getName());
        SkinDetailsFragment skinDetailsFragment = new SkinDetailsFragment();
        skinDetailsFragment.setArguments(bundle);
        return skinDetailsFragment;
    }

    public static SkinDetailsFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_skin_details, viewGroup, false);
        loadRewardedVideoAd();
        Yodo1MasNativeAdView nativeAdView =
                new Yodo1MasNativeAdView(getContext());
        nativeAdView.setLayoutParams(new
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(getContext(), 300)));


        nativeAdView = view.findViewById(R.id.yodo1_mas_native);
        nativeAdView.loadAd();
        instance = this;
        Skin skin = getSkin();
        this.skin = skin;
        if (skin == null) {
            throw new IllegalArgumentException("Missing skin data");
        }
        ConstantFunctions.loadImageWithoutThumbnail(BaseURL.createThumbnailPath(skin.getNumber()), (ImageView) this.view.findViewById(R.id.skin_container));
        this.view.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SkinDetailsFragment.this.closeScreen(view);
            }
        });
        this.view.findViewById(R.id.toolbar_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SkinDetailsFragment.this.goToStore(view);
            }
        });
        this.view.findViewById(R.id.skin_button_favorite).setOnClickListener(this.onFavoriteClicked);
        this.view.findViewById(R.id.skin_button_3d).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SkinDetailsFragment.this.addScreen(view);
            }
        });
        this.view.findViewById(R.id.skin_button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                SkinDetailsFragment.this.showConfirmIfEnabled(view);
            }
        });
        ((TextView) this.view.findViewById(R.id.skin_name)).setText(String.format(getString(R.string.skin_name_formatted), this.skin.getName()));
        updateFavoriteState();
        updateSkinInfo();
        if (!this.alreadyLoaded) {
            int opensWithoutAd = LocalStorage.getOpensWithoutAd(getContext());
            if (opensWithoutAd >= 2 || opensWithoutAd < 0) {
                if (getActivity() instanceof MainActivity) {
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    boolean isVIp = sharedPreferences.getBoolean("VIP", false);
                    if (!isVIp) {

                        ((MainActivity) getActivity()).showInterAd();
                        ((MainActivity) getActivity()).loadInterAd();
//                        ((MainActivity) getActivity()).loadRewardedVideoAd();
                    }
                }
            } else {
                LocalStorage.setOpensWithoutAd(getContext(), opensWithoutAd + 1);
            }
        }
        this.view.findViewById(R.id.info_container).setVisibility(View.GONE);
        return this.view;
    }


    void closeScreen(View view) {
        this.mainManager.closeScreen();
    }


    void goToStore(View view) {
        Helpers.goToStore(getContext());
    }


    void addScreen(View view) {
        this.mainManager.addScreen(Skin3dFragment.createInstance(this.skin));
    }

    void showConfirmIfEnabled(View view) {
        showConfirmIfEnabled(new Runnable() {
            public final void run() {
                SkinDetailsFragment.this.m45xa5893f96();
            }
        });
    }

    void m45xa5893f96() {
        this.actionType = ActionType.Gallery;
        requestOrRun(new Runnable() {
            @Override
            public final void run() {
                SkinDetailsFragment.this.processActions();
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        if (bundle != null || this.alreadyLoaded) {
            return;
        }
        this.alreadyLoaded = true;
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == WRITE_PERMISSION_CODE13) {
            if (iArr.length > 0 && iArr[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                processActions();
            } else {
                // Permission denied
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (i == WRITE_PERMISSION_CODE && strArr.length == 1 && iArr.length == 1 && "android.permission.WRITE_EXTERNAL_STORAGE".equalsIgnoreCase(strArr[0]) && iArr[0] == 0) {
            processActions();
        }
    }

    private void updateFavoriteState() {
        ((ImageView) this.view.findViewById(R.id.skin_button_favorite)).setImageResource(FavoritesManager.getInstance().isFavorite(getContext(), this.skin.getNumber()) ? R.drawable.skin_button_favorite_on : R.drawable.skin_button_favorite_off);
    }

    private Skin getSkin() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return null;
        }
        String string = arguments.getString(EXTRA_POSITION, null);
        String string2 = arguments.getString(EXTRA_NAME, null);
        if (string == null || string2 == null) {
            return null;
        }
        return new Skin(string, string2);
    }

    private void updateSkinInfo() {
        ((TextView) this.view.findViewById(R.id.skin_views_count)).setText("345567");
        ((TextView) this.view.findViewById(R.id.skin_downloads_count)).setText("3456");
        ((TextView) this.view.findViewById(R.id.skin_size_count)).setText("64x64");
    }

    private void requestOrRun(Runnable runnable) {
        if (getActivity() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, WRITE_PERMISSION_CODE13);
            } else {
                runnable.run();
            }
        } else {
            // Request other permissions for lower versions
            if (checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, WRITE_PERMISSION_CODE);
            } else {
                runnable.run();
            }
        }

    }

    public void processActions() {
        if (getActivity() instanceof MainActivity) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
            boolean isVIp = sharedPreferences.getBoolean("VIP", false);
            if (!isVIp) {
//                ((MainActivity) getActivity()).showVideoAd();
                showVideoAdlist();
            }
            startAction();
        }
    }

    public void startAction() {
        if (this.actionType == null || this.skin == null) {
            return;
        }
        try {
            File cachedImage = getCachedImage();
            int i = AnonymousClass1.$SwitchMap$com$moliyacentre$topcamouflageskinsmcpe$fragment$SkinDetailsFragment$ActionType[this.actionType.ordinal()];
            if (i == 1) {
                File createFileInGallery = Helpers.createFileInGallery(getContext(), this.skin.getNumber());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Helpers.saveImageToGallery(requireContext(), cachedImage, skin.getNumber());
                } else {
                    Helpers.copyFile(cachedImage, createFileInGallery);
                }
                MediaScannerConnection.scanFile(Myapplication.getContext(), new String[]{createFileInGallery.getPath()}, null, null);
                alertAfterSaved();
            } else if (i == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Helpers.saveImageToGallery(requireContext(), cachedImage, skin.getNumber());
                } else {
                    Helpers.copyFile(cachedImage, Helpers.createFileInApplication("custom"));
                }
                alertAfterInstalled();
            }
        } catch (Exception e) {
            if (getContext() != null) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    static class AnonymousClass1 {
        static final int[] $SwitchMap$com$moliyacentre$topcamouflageskinsmcpe$fragment$SkinDetailsFragment$ActionType;

        static {
            int[] iArr = new int[ActionType.values().length];
            $SwitchMap$com$moliyacentre$topcamouflageskinsmcpe$fragment$SkinDetailsFragment$ActionType = iArr;
            try {
                iArr[ActionType.Gallery.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$moliyacentre$topcamouflageskinsmcpe$fragment$SkinDetailsFragment$ActionType[ActionType.Application.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void showConfirmIfEnabled(final Runnable runnable) {
        Objects.requireNonNull(runnable);
        ConfirmDialog.createDialog(new ConfirmDialog.OnFinishListener() {
            @Override
            public final void onFinish() {
                runnable.run();
            }
        }).show(getChildFragmentManager(), "dialog");
    }

    private File getCachedImage() {
        return new File(getContext().getCacheDir(), String.format("%s.png", this.skin.getNumber()));
    }

    private void alertAfterInstalled() {
        this.mainManager.showDialog(SavedToMinecraftDialog.createDialog(null));
    }

    private void alertAfterSaved() {
        this.mainManager.showDialog(SavedToGalleryDialog.createDialog(null));
    }

    void m41x2e4c118e(View view) {
        if (getContext() == null || this.skin == null) {
            return;
        }
        FavoritesManager favoritesManager = FavoritesManager.getInstance();
        if (favoritesManager.isFavorite(getContext(), this.skin.getNumber())) {
            favoritesManager.removeFavorite(getContext(), this.skin.getNumber());
        } else {
            favoritesManager.addToFavorite(getContext(), this.skin.getNumber());
        }
        updateFavoriteState();
    }

    public void loadRewardedVideoAd() {
        Yodo1MasRewardAd.getInstance().setAdListener(new Yodo1MasRewardAdListener() {

            @Override
            public void onRewardAdLoaded(Yodo1MasRewardAd ad) {


            }


            @Override
            public void onRewardAdFailedToLoad(Yodo1MasRewardAd ad, @NonNull Yodo1MasError error) {

                ad.loadAd(((MainActivity) getActivity()));

            }

            @Override
            public void onRewardAdOpened(Yodo1MasRewardAd ad) {

            }

            @Override
            public void onRewardAdFailedToOpen(Yodo1MasRewardAd ad, @NonNull Yodo1MasError error) {
                ad.loadAd(getActivity());
            }

            @Override
            public void onRewardAdClosed(Yodo1MasRewardAd ad) {
                ad.loadAd(getActivity());
            }

            @Override
            public void onRewardAdEarned(Yodo1MasRewardAd ad) {

            }
        });
    }

    public void showVideoAdlist() {
        boolean isLoaded = Yodo1MasRewardAd.getInstance().isLoaded();
        if (isLoaded) {
            Yodo1MasRewardAd.getInstance().showAd(getActivity(), "Your Placement");


        }

    }



}
