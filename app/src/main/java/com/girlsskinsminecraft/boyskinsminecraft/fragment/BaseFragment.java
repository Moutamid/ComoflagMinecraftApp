package com.girlsskinsminecraft.boyskinsminecraft.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.girlsskinsminecraft.boyskinsminecraft.IMainManager;


public abstract class BaseFragment extends Fragment {
    protected IMainManager mainManager;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getActivity() instanceof IMainManager) {
            this.mainManager = (IMainManager) getActivity();
        }
    }

    @Override 
    public void onDestroy() {
        super.onDestroy();
        this.mainManager = null;
    }
}
