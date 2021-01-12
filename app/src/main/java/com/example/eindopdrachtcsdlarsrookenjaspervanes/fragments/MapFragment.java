package com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Data;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.R;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.geofencing.GeoFenceSetup;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.okhttp.OpenRouteService;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.MapViewModel;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.viewModels.ViewModel;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapFragment extends Fragment implements LifecycleOwner {

    private ViewModel mViewModel;

    private Context fragmentContext;
    private IMapController mapController;
    private MapView mapView;
    private ImageButton ibCenterMap;

    private boolean isCenterMode;
    private Marker currentLocation;

    private OpenRouteService openRouteService;
    private GeoFenceSetup setupGF;

    public static MapFragment newInstance() {
        return new MapFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentContext = getActivity().getBaseContext();
        Configuration.getInstance().load(fragmentContext, PreferenceManager.getDefaultSharedPreferences(fragmentContext));
        Data.getInstance().setCurrentFragment(this);
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        mapView = view.findViewById(R.id.mapView);
        mapView.setUseDataConnection(true);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        mapController = mapView.getController();
        mapController.setZoom(14);

        isCenterMode = true;

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }

        mapView.setMultiTouchControls(true);
        mapView.setBuiltInZoomControls(true);

        openRouteService = new OpenRouteService(mapView, fragmentContext, view);
        setupGF = new GeoFenceSetup(requireActivity());

        refreshMap();

        ibCenterMap = view.findViewById(R.id.ibCenterMap);
        ibCenterMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLocation != null) {
                    isCenterMode = !isCenterMode;
                    if (isCenterMode)
                        Toast.makeText(fragmentContext, "You are now in center-mode", Toast.LENGTH_SHORT).show();
                    if (!isCenterMode)
                        Toast.makeText(fragmentContext, "You are no longer in center-mode", Toast.LENGTH_SHORT).show();
//                    mapController.setCenter(mViewModel.getCurrentLocation().getValue());
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getLocation();
    }

    public void getLocation() {
        LocationManager locationManager = (LocationManager) fragmentContext.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (getView() == null) {
                    return;
                }

                Log.d("Latitude", "onLocationChanged: " + location.getLatitude());
                GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
                if (isCenterMode)
                    mapController.setCenter(point);
                Marker startPoint = new Marker(mapView);
                startPoint.setPosition(point);
                startPoint.setIcon(getResources().getDrawable(R.drawable.my_location));
                mapView.getOverlays().remove(currentLocation);

                currentLocation = startPoint;
                mViewModel.setCurrentLocation(currentLocation.getPosition());

                //removes old location of user
                List<GeoPoint> tempList = new ArrayList<>();
                if (mViewModel.getActiveRoute() != null)
                    tempList.add(mViewModel.getActiveRoute().getWaypoints().get(0));
                setupGF.removeGeoFences(tempList);

                //adds new location of user
                tempList.clear();
                tempList.add(currentLocation.getPosition());
                setupGF.setupGeoFencing(tempList);

                refreshMap();

                mapView.getOverlays().add(startPoint);
            }
        };

        if (ContextCompat.checkSelfPermission(fragmentContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, locationListener);
        }
    }

    public void refreshMap() {
        if (mViewModel.getActiveRoute() != null && mViewModel.getActiveRoute().isActive == 1) {
            openRouteService.getRoute(mViewModel.getActiveRoute().getWaypoints().toArray(
                    new GeoPoint[mViewModel.getActiveRoute().getWaypoints().size()]),
                    mViewModel.getActiveRoute().getMethod(),
                    "en", R.drawable.waypoint_marker, Color.RED);

            if (!mViewModel.getIsGeofencing().getValue()) {
                List<GeoPoint> waypoints = mViewModel.getActiveRoute().getWaypoints();
                setupGF.setupGeoFencing(waypoints);
                mViewModel.setIsGeofencing(true);
                Log.d("@@@@@@@@@@@@@@", "Geofencing is setup");
            } else if (mViewModel.getActiveRoute() != null) {
                setupGF.removeGeoFences(mViewModel.getActiveRoute().getWaypoints());
                mViewModel.setIsGeofencing(false);
            }
        }
    }
}