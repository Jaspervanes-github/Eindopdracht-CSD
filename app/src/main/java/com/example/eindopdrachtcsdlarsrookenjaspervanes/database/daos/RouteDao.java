package com.example.eindopdrachtcsdlarsrookenjaspervanes.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;

import org.osmdroid.util.GeoPoint;

import java.util.List;

@Dao
public interface RouteDao {
    @Query("SELECT * FROM Route")
    List<Route> getAll();

    @Query("SELECT * FROM Route WHERE uid IN (:routeIds)")
    List<Route> loadAllByIds(int[] routeIds);

    @Query("SELECT waypoints FROM Route WHERE uid IN (:routeId)")
    List<GeoPoint> loadRouteFromId(int routeId);

    @Query("SELECT * FROM Route WHERE uid = :uid")
    Route findRouteByID(int uid);

    @Query("SELECT * FROM Route WHERE isActive = 1 LIMIT 1")
    Route getActiveRoute();

    @Query("UPDATE Route set isActive = 1 WHERE uid = :routeID")
    void setActiveRoute(int routeID);

    @Query("UPDATE Route set isActive = 0 WHERE uid = :routeID")
    void unActiveRoute(int routeID);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRoute(Route routes);

    @Delete
    void delete(Route route);

    @Query("UPDATE Route SET waypoints = :route WHERE uid = :uid")
    void updateRoute(int uid, List<GeoPoint> route);

    @Query("UPDATE Route SET from_location = :isFromLocation WHERE uid = :uid")
    void updateRoute(int uid, boolean isFromLocation);

    @Query("UPDATE Route SET from_location = :isFromLocation, waypoints = :route  WHERE uid = :uid")
    void updateRoute(int uid, boolean isFromLocation, List<GeoPoint> route);

}
