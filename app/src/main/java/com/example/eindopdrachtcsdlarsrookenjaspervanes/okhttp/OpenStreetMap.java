package com.example.eindopdrachtcsdlarsrookenjaspervanes.okhttp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

public class OpenStreetMap {

    public void drawRoute(MapView mapView, ArrayList<GeoPoint> geoPoints, int color) {
        Polyline line = new Polyline();
        line.setPoints(geoPoints);
        line.setColor(color);
        mapView.getOverlayManager().add(line);
    }

    public void drawMarker(MapView mapView, GeoPoint point, Drawable icon){
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setIcon(icon);
        mapView.getOverlays().add(marker);
    }
}
