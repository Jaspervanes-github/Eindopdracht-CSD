package com.example.eindopdrachtcsdlarsrookenjaspervanes.geofencing;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class GeoFenceSetup {

    private Context context;
    private Activity appActivity;
    private GeofencingClient geofencingClient;
    private GeoFenceHelper geoFenceHelper;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;

    public GeoFenceSetup(Context context, Activity appActivity) {
        this.context = context;
        this.appActivity = appActivity;
    }

    public void setupGeoFencing(List<GeoPoint> waypoints) {
        checkFineLocationPermission();

        geofencingClient = LocationServices.getGeofencingClient(context);
        geoFenceHelper = new GeoFenceHelper(context);

        if (Build.VERSION.SDK_INT >= 29) {
            //If API is higher then 29 we need background permission
            System.out.println("in if in setupgf");
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {//ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
                addFences(waypoints);
            } else {
                //Permission is not granted!! Need to request it..
                if (ActivityCompat.shouldShowRequestPermissionRationale(appActivity, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    //We show a dialog and ask for permission
                    ActivityCompat.requestPermissions(appActivity, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(appActivity, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);

                }
            }
        } else {
            addFences(waypoints);
        }


    }

    private void addFences(List<GeoPoint> waypoints) {

        System.out.println(":::::" + waypoints.size());

        for (int i = 0; i < waypoints.size(); i++) {
            System.out.println(waypoints.get(i).getLongitude() + "---" + waypoints.get(i).getLatitude());
            GeoPoint tempPoint = new GeoPoint(waypoints.get(i).getLatitude(), waypoints.get(i).getLongitude());
            addGeoFence(tempPoint, 50, i + "");
        }
    }

    private void addGeoFence(GeoPoint geoPoint, float radius, String ID) {
        checkFineLocationPermission();
        System.out.println("In ADDGEOFENCE: " + geoPoint.getLongitude() + " : " + geoPoint.getLatitude());

        Geofence geofence = geoFenceHelper.getGeofence(ID, geoPoint, radius,
                Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);


        GeofencingRequest geofencingRequest = geoFenceHelper.getGeoFencingRequest(geofence);
        PendingIntent pendingIntent = geoFenceHelper.getPendingIntent();


        geofencingClient.addGeofences(geofencingRequest, pendingIntent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("SUCCESSFULLY ADDED A POINT");
                Log.v(TAG, "Geofence is added... ");
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("FAILED TO ADD POINT");
                Log.v(TAG, e.getLocalizedMessage());
            }

        });
    }

    public void removeGeoFences(List<GeoPoint> waypoints) {
        geofencingClient = LocationServices.getGeofencingClient(context);
        geoFenceHelper = new GeoFenceHelper(context);
        PendingIntent pendingIntent = geoFenceHelper.getPendingIntent();


        List<String> waypointsList = new ArrayList<>();

        for (int i = 0; i < waypoints.size(); i++) {
            waypointsList.add("#" + i);
        }


        System.out.println("@@@@@@@@@@@@@@" + waypointsList.size());

        geofencingClient.removeGeofences(pendingIntent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.v(TAG, "Geofence is removed... ");
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v(TAG, e.getLocalizedMessage());
            }


        });

        waypointsList.clear();
    }


    private void checkFineLocationPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }
}
