package com.example.eindopdrachtcsdlarsrookenjaspervanes.database;

import androidx.room.TypeConverter;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class RoomConverter {
    @TypeConverter
    public static GeoPoint getGeoPoint(String geoPoint) {
        String[] segments = geoPoint.split("__");
        return new GeoPoint(Double.parseDouble(segments[0]), Double.parseDouble(segments[1]));
    }

    @TypeConverter
    public static String getStringGeoPoint(GeoPoint geoPoint) {
        return String.format(Locale.ENGLISH, "%f__%f", geoPoint.getLatitude(), geoPoint.getLongitude());
    }


    @TypeConverter
    public static List<GeoPoint> getRoute(String listString) {
        if (listString == null || listString.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(listString.split("--"))
                .map(RoomConverter::getGeoPoint)
                .collect(Collectors.toList());
    }

    @TypeConverter
    public static String getRouteString(List<GeoPoint> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        return list.stream()
                .map(RoomConverter::getStringGeoPoint)
                .collect(Collectors.joining("--"));
    }
}
