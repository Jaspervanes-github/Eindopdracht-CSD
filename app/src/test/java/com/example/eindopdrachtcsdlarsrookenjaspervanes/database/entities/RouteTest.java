package com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.Method;

import org.junit.Before;
import org.junit.Test;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RouteTest {

    private List<GeoPoint> testWaypoints;
    private Route route;

    @Before
    public void beforeTest(){
        String method = Method.DRIVING_CAR.toString().replaceAll("_", "-").toLowerCase();
        testWaypoints = new ArrayList<>();
        testWaypoints.add(new GeoPoint(51.4, 4.1));
        testWaypoints.add(new GeoPoint(48.4, 4.1));
        this.route = new Route("testRoute", testWaypoints, method, false);
    }

    @Test
    public void getUid() {
        int id = this.route.getUid();
        assertEquals(id, 0);
    }

    @Test
    public void setUid() {
        int testID = 5;
        this.route.setUid(testID);

        int id = this.route.getUid();

        assertEquals(id, testID);
    }

    @Test
    public void getName() {
        String testName = this.route.getName();
        assertEquals(testName, "testRoute");
    }

    @Test
    public void setName() {
        String testName = "newTestName";
        this.route.setName(testName);

        String name = this.route.getName();

        assertEquals(name, testName);
    }

    @Test
    public void getWaypoints() {
        List<GeoPoint> testWaypoints = this.route.getWaypoints();
        assertEquals(testWaypoints, this.testWaypoints);
    }

    @Test
    public void setWaypoints() {
        List<GeoPoint> newTestWaypoints = new ArrayList<>();
        newTestWaypoints.add(new GeoPoint(51.4, 4.1));

        this.route.setWaypoints(newTestWaypoints);
        List<GeoPoint> wayPoints = this.route.getWaypoints();

        assertEquals(wayPoints, newTestWaypoints);
    }

    @Test
    public void isFromLocation() {
        boolean fromLocation = this.route.isFromLocation();
        assertEquals(fromLocation, false);
    }

    @Test
    public void setFromLocation() {
        boolean newFromLocation = true;
        this.route.setFromLocation(newFromLocation);

        boolean fromLocation = this.route.isFromLocation();

        assertEquals(fromLocation, newFromLocation);
    }

    @Test
    public void isActive() {
        boolean isActive = this.route.isActive();
        assertEquals(isActive, false);
    }

    @Test
    public void setActive() {
        boolean newIsActive = true;
        this.route.setActive(newIsActive);

        boolean isActive = this.route.isActive();

        assertEquals(isActive, newIsActive);
    }

    @Test
    public void getMethod() {
        Method method = Method.valueOf(this.route.getMethod().replace("-", "_").toUpperCase());
        assertEquals(method, Method.DRIVING_CAR);
    }

    @Test
    public void setMethod() {
        Method newMethod = Method.FOOT_WALKING;
        this.route.setMethod(newMethod .toString().replaceAll("_", "-").toLowerCase());

        Method method = Method.valueOf(this.route.getMethod().replace("-", "_").toUpperCase());

        assertEquals(method, newMethod);
    }
}