package com.example.eindopdrachtcsdlarsrookenjaspervanes.models;

import org.osmdroid.util.GeoPoint;


import java.util.*;

public class EndPoint {

    private String name;
    private GeoPoint endPoint;
    private ArrayList<GeoPoint> pointInBetween;


    public EndPoint(String nameDestination, GeoPoint endPoint){
        this.name = nameDestination;
        this.endPoint = endPoint;
        this.pointInBetween = new ArrayList<>();
        this.pointInBetween.add(new GeoPoint(51.794920, 4.654280));
        this.pointInBetween.add(new GeoPoint(51.794970, 4.654290 ));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(GeoPoint endPoint) {
        this.endPoint = endPoint;
    }

    public ArrayList<GeoPoint> getPointInBetween() {
        return pointInBetween;
    }

    public void setPointInBetween(ArrayList<GeoPoint> pointInBetween) {
        this.pointInBetween = pointInBetween;
    }
}
