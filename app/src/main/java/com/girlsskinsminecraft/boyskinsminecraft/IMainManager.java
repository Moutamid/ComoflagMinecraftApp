package com.girlsskinsminecraft.boyskinsminecraft;

import androidx.fragment.app.DialogFragment;
import com.girlsskinsminecraft.boyskinsminecraft.fragment.BaseFragment;


public interface IMainManager {
    void addScreen(BaseFragment baseFragment);

    void closeScreen();

    void setScreen(BaseFragment baseFragment);

    void showDialog(DialogFragment dialogFragment);
}
