package com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.osmdroid.util.GeoPoint;

import java.util.List;

@Entity(tableName = "Route")
public class Route {

    public Route(String name, List<GeoPoint> waypoints, boolean isFromLocation) {
        this.name = name;
        this.waypoints = waypoints;
        this.isFromLocation = isFromLocation;
    }

    @PrimaryKey (autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "waypoints")
    public List<GeoPoint> waypoints;

    @ColumnInfo(name = "from_location")
    public boolean isFromLocation;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeoPoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<GeoPoint> waypoints) {
        this.waypoints = waypoints;
    }

    public boolean isFromLocation() {
        return isFromLocation;
    }

    public void setFromLocation(boolean fromLocation) {
        isFromLocation = fromLocation;
    }
}
