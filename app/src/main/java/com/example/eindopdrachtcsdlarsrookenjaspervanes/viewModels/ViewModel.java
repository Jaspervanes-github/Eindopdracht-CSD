package com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.Database;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.daos.RouteDao;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;

import org.osmdroid.util.GeoPoint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<EndPoint>> allEndPoints;
    private MutableLiveData<Route> selectedEndPoint;

    private MutableLiveData<Boolean> isGeofencing;
    private MutableLiveData<GeoPoint> currentLocation;

    private MutableLiveData<Activity> mainActivity;

    private RouteDao routeDao;

    public ViewModel(@NonNull Application application) {
        super(application);

        this.allEndPoints = new MutableLiveData<>();
        this.allEndPoints.setValue(new ArrayList<>());
        this.selectedEndPoint = new MutableLiveData<>();

        this.isGeofencing = new MutableLiveData<>(false);
        this.currentLocation = new MutableLiveData<>();

        this.mainActivity = new MutableLiveData<>();

        Database db = Database.getInstance(application);
        routeDao = db.routeDao();
    }

    public LiveData<ArrayList<EndPoint>> getAllEndPoints() {
        return allEndPoints;
    }

    public void setAllEndPoints(ArrayList<EndPoint> allEndPoints) {
        this.allEndPoints.setValue(allEndPoints);
    }

    public LiveData<Route> getSelectedEndPoint() {
        return selectedEndPoint;
    }

    public void setSelectedEndPoint(Route selectedRoute) {
        this.selectedEndPoint.setValue(selectedRoute);
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


    public List<Route> getAllSavedRoutes(){
        return this.routeDao.getAll();
    }

    public void addRoute(Route route){
        this.routeDao.insertRoute(route);
    }

    public void deleteRoute(Route route){
        this.routeDao.delete(route);
    }

    public void setActiveRoute(Route route){
        route.isActive = 1;
    }
    public void unActiveRoute(Route route){
        route.isActive = 0;
    }

    public Route getActiveRoute(){
       return this.routeDao.getActiveRoute();
    }
}
