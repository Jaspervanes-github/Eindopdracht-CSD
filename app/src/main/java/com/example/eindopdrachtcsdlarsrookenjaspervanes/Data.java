package com.example.eindopdrachtcsdlarsrookenjaspervanes;

import androidx.fragment.app.Fragment;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
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
    private Route activeRoute;

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

    public void setActiveRoute(Route activeRoute){
        this.activeRoute = activeRoute;
    }

    public Route getActiveRoute(){
        return this.activeRoute;
    }
}
