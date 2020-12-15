package com.example.eindopdrachtcsdlarsrookenjaspervanes;

import androidx.fragment.app.Fragment;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments.MapFragment;

public class Data {

    private static Data data;

    public static Data getInstance(){
        if(data == null){
            data = new Data();
            return data;
        }
        else
            return data;
    }


    private Fragment currentFragment;
    private boolean dataLoaded;

    public Data() {
        this.currentFragment = new MapFragment();
        this.dataLoaded = false;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public void setDataLoaded(boolean dataLoaded) {
        this.dataLoaded = dataLoaded;
    }
}
