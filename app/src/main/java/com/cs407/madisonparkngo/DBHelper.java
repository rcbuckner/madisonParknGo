package com.cs407.madisonparkngo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ParkingLot.class}, version = 1)
public abstract class DBHelper extends RoomDatabase {

    public abstract UserDao userDao();

    private static DBHelper INSTANCE;

    public static DBHelper getDBInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DBHelper.class, "parkinglot")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
