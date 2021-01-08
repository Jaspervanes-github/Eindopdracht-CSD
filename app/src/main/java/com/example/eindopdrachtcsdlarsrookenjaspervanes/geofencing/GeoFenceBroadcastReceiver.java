package com.example.eindopdrachtcsdlarsrookenjaspervanes.geofencing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import static android.content.ContentValues.TAG;

public class GeoFenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);


        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive BrCastReceiver Error ");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();

        System.out.println("@@##@#@#@#@@#@# Triggered Geofences: ");
        for (int i = 0; i < geofenceList.size(); i++) {
            System.out.println(geofenceList.get(i).getRequestId());

        }

        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER: {
                Toast.makeText(context, "GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_LONG).show();
                Log.d(TAG, "GEOFENCE_TRANSITION_ENTER");

//                if(id != #0){
//                    you have entered a waypoint, show toast
//                }else if(id == last waypoint){
//                    toast(you have arrived at destination your final destination)
//            }
                break;
            }
            case Geofence.GEOFENCE_TRANSITION_DWELL: {
                Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_LONG).show();
                Log.d(TAG, "GEOFENCE_TRANSITION_DWELL");
                break;
            }
            case Geofence.GEOFENCE_TRANSITION_EXIT: {
                Toast.makeText(context, "GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_LONG).show();
                Log.d(TAG, "GEOFENCE_TRANSITION_EXIT");

//                if(id == #0){
//                    calculate route and set in viewmodel
//                (may want to only calculate route from location to next waypoint, this is better for performance)??? does it matter
//                }

                break;
            }


        }
    }
}
