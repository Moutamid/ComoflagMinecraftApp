package com.girlsskinsminecraft.boyskinsminecraft.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.girlsskinsminecraft.boyskinsminecraft.R;
import com.girlsskinsminecraft.boyskinsminecraft.utils.Helpers;


public class PolicyFragment extends BaseFragment {
    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_polity, viewGroup, false);
        inflate.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                PolicyFragment.this.closeScreen(view);
            }
        });
        inflate.findViewById(R.id.toolbar_rate).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public final void onClick(View view) {
                PolicyFragment.this.goToStore(view);
            }
        });
        ((WebView) inflate.findViewById(R.id.container)).loadUrl("file:///android_asset/policy.html");
        return inflate;
    }

    
     void closeScreen(View view) {
        this.mainManager.closeScreen();
    }

    
     void goToStore(View view) {
        Helpers.goToStore(getContext());
    }
}
