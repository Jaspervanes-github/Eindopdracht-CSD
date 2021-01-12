package com.example.eindopdrachtcsdlarsrookenjaspervanes.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.daos.RouteDao;
import com.example.eindopdrachtcsdlarsrookenjaspervanes.database.entities.Route;

@TypeConverters(RoomConverter.class)
@androidx.room.Database(entities = {Route.class}, version = 4, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract RouteDao routeDao();

    private static Database database;
    private static String DATABASE_NAME = "database";

    public synchronized static Database getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    Database.class, DATABASE_NAME).
                    allowMainThreadQueries().
                    fallbackToDestructiveMigration().
                    build();
        }
        return database;
    }
}
