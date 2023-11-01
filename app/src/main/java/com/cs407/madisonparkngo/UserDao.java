package com.cs407.madisonparkngo;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM parkinglot")
    List<ParkingLot> getAllLots();

}
