package com.example.eindopdrachtcsdlarsrookenjaspervanes.okhttp;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

public class OpenStreetMap {

    public void drawRoute(MapView mapView, ArrayList<GeoPoint> geoPoints) {
        Polyline line = new Polyline();
        line.setPoints(geoPoints);
        mapView.getOverlayManager().add(line);
    }

    public void drawMarker(MapView mapView, GeoPoint point){
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        mapView.getOverlays().add(marker);
    }
}
