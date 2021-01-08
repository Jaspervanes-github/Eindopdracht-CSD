package com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.osmdroid.util.GeoPoint;

import java.util.List;

@Entity(tableName = "Route")
public class Route {
    public Route() {
    }

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "waypoints")
    public List<GeoPoint> waypoints;

    @ColumnInfo(name = "from_location")
    public boolean isFromLocation;
}
