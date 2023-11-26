package com.cs407.madisonparkngo;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM parkinglot")
    List<ParkingLot> getAllLots();

    @Query("SELECT * FROM parkinglot WHERE motorcycle = 1")
    List<ParkingLot> getMotorcycleLots();

    @Query("SELECT * FROM parkinglot WHERE car = 1")
    List<ParkingLot> getCarLots();

    @Query("SELECT * FROM parkinglot WHERE moped = 1")
    List<ParkingLot> getMopedLots();

    @Query("SELECT * FROM parkinglot WHERE typeOfLot LIKE :lotType")
    List<ParkingLot> getTypeOfLot(String lotType);

    @Query("SELECT * FROM parkinglot WHERE permit LIKE :permit")
    List<ParkingLot> getIfPermit(String permit);

    @Query("SELECT * FROM parkinglot WHERE cost < :limit")
    List<ParkingLot> getPricedLots(int limit);

}
