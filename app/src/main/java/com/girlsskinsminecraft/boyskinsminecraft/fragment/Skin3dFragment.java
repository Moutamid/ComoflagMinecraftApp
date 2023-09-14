package com.girlsskinsminecraft.boyskinsminecraft.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.girlsskinsminecraft.boyskinsminecraft.R;
import com.girlsskinsminecraft.boyskinsminecraft.model.Skin;
import com.girlsskinsminecraft.boyskinsminecraft.utils.BaseURL;
import java.io.File;


public class Skin3dFragment extends BaseFragment {
    private static final String EXTRA_NAME = "extra_name";
    private static final String EXTRA_POSITION = "extra_position";
    private Skin skin;
    private View view;

    public static Skin3dFragment createInstance(Skin skin) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_POSITION, skin.getNumber());
        bundle.putString(EXTRA_NAME, skin.getName());
        Skin3dFragment skin3dFragment = new Skin3dFragment();
        skin3dFragment.setArguments(bundle);
        return skin3dFragment;
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_3d_preview, viewGroup, false);
        Skin skin = getSkin();
        this.skin = skin;
        if (skin == null) {
            throw new IllegalArgumentException("Missing skin data");
        }
        ((TextView) this.view.findViewById(R.id.skin_name)).setText(String.format(getString(R.string.skin_name_formatted), this.skin.getName()));
        this.view.findViewById(R.id.toolbar_close).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                Skin3dFragment.this.closeScreen(view);
            }
        });
        WebView webView = (WebView) this.view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(0);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.loadUrl("");
        webView.loadUrl("file:///android_asset/index.html?url=" + BaseURL.createSkinPath(this.skin.getNumber()));
        Log.d("TAG", "dir = " + getCachedImage().getAbsolutePath());
        return this.view;

    }

    
     void closeScreen(View view) {
        this.mainManager.closeScreen();
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

    private File getCachedImage() {
        return new File(getContext().getCacheDir(), String.format("%s.png", this.skin.getNumber()));
    }
}
