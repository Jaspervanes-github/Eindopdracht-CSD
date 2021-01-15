package com.example.eindopdrachtcsdlarsrookenjaspervanes.geofencing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import static android.content.ContentValues.TAG;

public class GeoFenceBroadcastReceiver extends BroadcastReceiver {

    private Route activeRoute = Data.getInstance().getActiveRoute();

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

                for (int i = 0; i < geofenceList.size(); i++) {
                    if (activeRoute.isFromLocation()) {
                        if (Integer.parseInt(geofenceList.get(i).getRequestId()) == activeRoute.getWaypoints().size() - 1) {
                            Toast.makeText(context, R.string.toast_reachedDestination, Toast.LENGTH_SHORT).show();
//                            mViewModel.unActiveRoute(activeRoute.getUid());
                        }
                    } else {
                        if (Integer.parseInt(geofenceList.get(i).getRequestId()) == activeRoute.getWaypoints().size() - 2) {
                            Toast.makeText(context, R.string.toast_reachedDestination, Toast.LENGTH_SHORT).show();
//                            mViewModel.unActiveRoute(activeRoute.getUid());
                        }
                    }
                }
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
                break;
            }


        }
    }
}
