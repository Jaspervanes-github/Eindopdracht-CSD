package com.example.eindopdrachtcsdlarsrookenjaspervanes.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;

import org.osmdroid.util.GeoPoint;

import java.util.List;

@Dao
public interface RouteDao {
    @Query("SELECT * FROM Route")
    Route getAll();

    @Query("SELECT * FROM Route WHERE uid IN (:routeIds)")
    Route loadAllByIds(int[] routeIds);

    @Query("SELECT waypoints FROM Route WHERE uid IN (:routeId)")
    List<GeoPoint> loadRouteFromId(int routeId);

    @Query("SELECT * FROM Route WHERE uid LIKE :uid")
    Route findByID(String uid);

    @Insert
    void insertAll(Route... routes);

    @Delete
    void delete(Route route);

    @Query("UPDATE Route SET waypoints = :route WHERE uid = :uid")
    void updateRoute(int uid, List<GeoPoint> route);

    @Query("UPDATE Route SET from_location = :isFromLocation WHERE uid = :uid")
    void updateRoute(int uid, boolean isFromLocation);
}
