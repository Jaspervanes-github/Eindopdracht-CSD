package com.example.eindopdrachtcsdlarsrookenjaspervanes;

import androidx.fragment.app.Fragment;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.fragments.RouteListFragment;

import org.junit.Before;
import org.junit.Test;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataTest {
    private Data data;

    @Before
    public void init() {
        data = Data.getInstance();
    }

    @Test
    public void getCurrentFragmentTest() {
        RouteListFragment testFragment = new RouteListFragment();
        data.setCurrentFragment(testFragment);

        assertEquals(testFragment, data.getCurrentFragment());
    }

    @Test
    public void setCurrentFragmentTest() {
        RouteListFragment testFragment = new RouteListFragment();
        data.setCurrentFragment(testFragment);

        assertEquals(testFragment, data.getCurrentFragment());
    }

    @Test
    public void setActiveRouteTest() {
        Route testRoute = new Route("testRoute", new ArrayList<GeoPoint>(), "driving-car", true);
        data.setActiveRoute(testRoute);

        assertEquals(testRoute, data.getActiveRoute());
    }

    @Test
    public void getActiveRouteTest() {
        Route testRoute = new Route("testRoute", new ArrayList<GeoPoint>(), "driving-car", true);
        data.setActiveRoute(testRoute);

        assertEquals(testRoute, data.getActiveRoute());
    }
}
