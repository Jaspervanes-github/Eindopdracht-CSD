package com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.Database;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.daos.RouteDao;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.models.EndPoint;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<EndPoint>> allEndPoints;

    private MutableLiveData<Boolean> isGeofencing;
    private MutableLiveData<GeoPoint> currentLocation;

    private MutableLiveData<Activity> mainActivity;

    private RouteDao routeDao;

    public ViewModel(@NonNull Application application) {
        super(application);

        this.allEndPoints = new MutableLiveData<>();
        this.allEndPoints.setValue(new ArrayList<>());

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

    public Route getRouteByID(int routeID){
        return this.routeDao.findRouteByID(routeID);
    }

    public List<Route> getAllSavedRoutes() {
        return this.routeDao.getAll();
    }

    public void addRoute(Route route) {
        long id = this.routeDao.insertRoute(route);
        route.setUid((int)id);
    }

    public void deleteRoute(Route route) {
        this.routeDao.delete(route);
    }

    public void setActiveRoute(int routeID) {
        if(this.getActiveRoute() != null) {
            if (this.getActiveRoute().getName().equals("###"))
                this.deleteRoute(this.getActiveRoute());
            else
                this.unActiveRoute(this.getActiveRoute().getUid());
        }
        this.routeDao.setActiveRoute(routeID);
        Data.getInstance().setActiveRoute(this.routeDao.getActiveRoute());
    }

    public void unActiveRoute(int routeID) {
        Route routeCheck = getRouteByID(routeID);
        if(routeCheck.getName().equals("###"))
            this.deleteRoute(routeCheck);
        else
            this.routeDao.unActiveRoute(routeID);

    }

    public Route getActiveRoute() {
        Log.d("BEFORE RETURN ROUTE", "in method getActiveRoute() in ViewModel");
        return this.routeDao.getActiveRoute();
    }

}
