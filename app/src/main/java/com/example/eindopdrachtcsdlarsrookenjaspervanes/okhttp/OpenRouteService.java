package com.example.eindopdrachtcsdlarsrookenjaspervanes.okhttp;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenRouteService {
    private OkHttpClient client;
    public boolean isConnected;

    private OpenStreetMap openStreetMap;
    private MapView mapView;

    private final String api_key = "5b3ce3597851110001cf6248cc7335a16be74902905bcba4a9d0eebf";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public OpenRouteService(MapView mapView) {
        this.client = new OkHttpClient();
        this.isConnected = false;
        this.openStreetMap = new OpenStreetMap();
        this.mapView = mapView;

        Connect();
    }

    private void Connect() {
        this.isConnected = true;
    }

    private Request createGetRequest(String method, String url) {
        Request request = new Request.Builder().url("\n" +
                "https://api.openrouteservice.org/v2/directions/" + method + "?api_key=" + this.api_key + url).build();
        return request;
    }

    private Request createPostRequest(String method, String json) {
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url("https://api.openrouteservice.org/v2/directions/" + method).
                post(requestBody).addHeader("Authorization", api_key).build();
        return request;
    }
//
//    private Request createPutRequest(String url, String json) {
//        RequestBody requestBody = RequestBody.create(json, JSON);
//        Request request = new Request.Builder().url("http://" + this.ipAddress + ":" + this.port + "/api/" + this.username + url).put(requestBody).build();
//        return request;
//    }
//
//    private Request createDeleteRequest(String url) {
//        Request request = new Request.Builder().url("http://" + this.ipAddress + ":" + this.port + "/api/" + this.username + url).delete().build();
//        return request;
//    }

    private static JSONArray decodeGeometry(String encodedGeometry, boolean inclElevation) {
        JSONArray geometry = new JSONArray();
        int len = encodedGeometry.length();
        int index = 0;
        int lat = 0;
        int lng = 0;
        int ele = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedGeometry.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedGeometry.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);


            if (inclElevation) {
                result = 1;
                shift = 0;
                do {
                    b = encodedGeometry.charAt(index++) - 63 - 1;
                    result += b << shift;
                    shift += 5;
                } while (b >= 0x1f);
                ele += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);
            }

            JSONArray location = new JSONArray();
            try {
                location.put(lat / 1E5);
                location.put(lng / 1E5);
                if (inclElevation) {
                    location.put((float) (ele / 100));
                }
                geometry.put(location);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return geometry;
    }

    public void getRoute(GeoPoint startPoint, GeoPoint endPoint, String method) {
        ArrayList<GeoPoint> points = new ArrayList<>();

        if (this.isConnected) {
            client.newCall(createGetRequest(method,
                    "&start=" + startPoint.getLongitude() + "," + startPoint.getLatitude()
                            + "&end=" + endPoint.getLongitude() + "," + endPoint.getLatitude()))
                    .enqueue(new Callback() {

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d("FAILURE", "In OnFailure() in getRoute() single");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            try {
                                JSONObject responseObject = new JSONObject(response.body().string());
                                JSONArray featureArray = responseObject.getJSONArray("features");
                                JSONObject feature = (JSONObject) featureArray.get(0);
                                JSONObject geometry = feature.getJSONObject("geometry");
                                JSONArray coordinates = geometry.getJSONArray("coordinates");

                                for (int i = 0; i < coordinates.length(); i++) {
                                    JSONArray coordArray = (JSONArray) coordinates.get(i);
                                    double lng = coordArray.getDouble(0);
                                    double lat = coordArray.getDouble(1);
                                    GeoPoint point = new GeoPoint(lat, lng);
                                    points.add(point);
                                }

                                openStreetMap.drawRoute(mapView, points);
                                openStreetMap.drawMarker(mapView, startPoint);
                                openStreetMap.drawMarker(mapView, endPoint);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    public void getRoute(GeoPoint[] waypoints, String method, String language) {
        if (this.isConnected) {
            ArrayList<GeoPoint> points = new ArrayList<>();
            double[][] coordinates = new double[waypoints.length][2];

            for (int i = 0; i < waypoints.length; i++) {
                coordinates[i][0] = waypoints[i].getLongitude();
                coordinates[i][1] = waypoints[i].getLatitude();
            }

            client.newCall(createPostRequest(method, "{\"coordinates\":" + Arrays.deepToString(coordinates) + ",\"language\":\"" + language + "\"}"))
                    .enqueue(new Callback() {

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d("FAILURE", "In OnFailure() in getRoute() multiple");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            try {
                                JSONObject responseObject = new JSONObject(response.body().string());
                                JSONArray routesArray = responseObject.getJSONArray("routes");
                                JSONObject routes = (JSONObject) routesArray.get(0);
                                String geometry = routes.getString("geometry");
                                JSONArray coordinates = decodeGeometry(geometry, false);

                                for (int i = 0; i < coordinates.length(); i++) {
                                    JSONArray cordArray = (JSONArray) coordinates.get(i);
                                    double lat = cordArray.getDouble(0);
                                    double lng = cordArray.getDouble(1);
                                    GeoPoint point = new GeoPoint(lat, lng);
                                    points.add(point);
//                                    System.out.println(i + ": " + cordArray.toString());
                                }
                                for (GeoPoint point : waypoints) {
                                    openStreetMap.drawMarker(mapView, point);
                                }
                                openStreetMap.drawRoute(mapView, points);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }
}