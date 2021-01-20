package com.example.eindopdrachtcsdlarsrookenjaspervanes;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.Database;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;


public class DatabaseTest {

    private Context context;
    private Database database;


    @Before
    public void createDb(){
        this.context = ApplicationProvider.getApplicationContext();
        this.database = Room.inMemoryDatabaseBuilder(this.context, Database.class).build();
    }

    @After
    public void closeDb(){
        this.database.close();
    }

    @Test
    public void testInsertAndSelect(){
        List<GeoPoint> testWaypoints = new ArrayList<>();
        testWaypoints.add(new GeoPoint(51.4, 4.1));
        String  method = Method.DRIVING_CAR.toString().replaceAll("_", "-").toLowerCase();
        Route testRoute = new Route("testRoute", testWaypoints, method, false);

        List<GeoPoint> testWaypoints2 = new ArrayList<>();
        testWaypoints2.add(new GeoPoint(48.1, 4.1));
        String  method2 = Method.FOOT_WALKING.toString().replaceAll("_", "-").toLowerCase();
        Route testRoute2 = new Route("testRoute2", testWaypoints2, method2, false);

        database.routeDao().insertRoute(testRoute);
        database.routeDao().insertRoute(testRoute2);

        List<Route> routes = database.routeDao().getAll();
        assertEquals(routes.get(0).getName(), "testRoute");
        assertEquals(routes.get(1).getName(), "testRoute2");
    }

    @Test
    public void TestDelete(){

    }

     @Test
    public void TestUpdate(){

    }
}