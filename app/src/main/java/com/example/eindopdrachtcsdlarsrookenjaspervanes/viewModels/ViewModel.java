package com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;

import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private MutableLiveData<ArrayList<EndPoint>> allEndPoints;
    private MutableLiveData<EndPoint> selectedEndPoint;

    private MutableLiveData<GeoPoint[]> currentRoute;
    private MutableLiveData<String> method;
    private MutableLiveData<Boolean> isFollowingRoute;
    private MutableLiveData<Boolean> isGeofencing;
    private MutableLiveData<GeoPoint> currentLocation;

    private MutableLiveData<Activity> mainActivity;


    public ViewModel() {
        this.allEndPoints = new MutableLiveData<>();
        this.allEndPoints.setValue(new ArrayList<>());
        this.selectedEndPoint = new MutableLiveData<>();

        this.currentRoute = new MutableLiveData<>();
        this.method = new MutableLiveData<>();
        this.isFollowingRoute = new MutableLiveData<>(false);
        this.isGeofencing = new MutableLiveData<>(false);
        this.currentLocation = new MutableLiveData<>();

        this.mainActivity = new MutableLiveData<>();
    }

    public LiveData<ArrayList<EndPoint>> getAllEndPoints() {
        return allEndPoints;
    }

    public void setAllEndPoints(ArrayList<EndPoint> allEndPoints) {
        this.allEndPoints.setValue(allEndPoints);
    }

    public LiveData<EndPoint> getSelectedEndPoint() {
        return selectedEndPoint;
    }

    public void setSelectedEndPoint(EndPoint selectedEndPoint) {
        this.selectedEndPoint.setValue(selectedEndPoint);
    }

    public LiveData<GeoPoint[]> getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(GeoPoint[] currentRoute) {
        this.currentRoute.setValue(currentRoute);
    }

    public LiveData<String> getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method.setValue(method);
    }

    public LiveData<Boolean> getIsFollowingRoute() {
        return isFollowingRoute;
    }

    public void setIsFollowingRoute(boolean isFollowingRoute) {
        this.isFollowingRoute.setValue(isFollowingRoute);
    }

    public LiveData<GeoPoint> getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(GeoPoint currentLocation) {
        this.currentLocation.setValue(currentLocation);
    }

    public LiveData<Activity> getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(Activity mainActivity) {
        this.mainActivity.setValue(mainActivity);
    }

    public LiveData<Boolean> getIsGeofencing() {
        return isGeofencing;
    }

    public void setIsGeofencing(boolean isGeofencing) {
        this.isGeofencing.setValue(isGeofencing);
    }
}
