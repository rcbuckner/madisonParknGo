package com.cs407.madisonparkngo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ParkingLot {
    @PrimaryKey(autoGenerate = false)
    private int lotID;
    private String name;
    private String address;
    private float latitude;
    private float longitude;
    private int motorcycle;
    private int car;
    private int moped;
    private String typeOfLot;
    private String permit;
    private float cost;
    private String timeOpen;
    private String timeClose;
    private String specialInfo;


    public ParkingLot(int lotID, String name, String address, int motorcycle, int car, int moped,
                      String typeOfLot, String permit, float cost, String timeOpen, String timeClose,
                      String specialInfo, float latitude, float longitude) {
        this.lotID = lotID;
        this.name = name;
        this.address = address;
        this.motorcycle = motorcycle;
        this.car = car;
        this.moped = moped;
        this.typeOfLot = typeOfLot;
        this.permit = permit;
        this.cost = cost;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
        this.specialInfo = specialInfo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getLotID(){return lotID;}
    public String getName(){return name;}
    public String getAddress(){return address;}
    public float getLongitude() {return longitude;}
    public float getLatitude() {return latitude;}
    public int getMotorcycle(){return  motorcycle;}
    public int getCar() {return car;}
    public int getMoped() {return moped;}

    public Boolean allowsMotorcycle(){
        if(motorcycle == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean allowsCar(){
        if(car == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean allowsMoped(){
        if(moped == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String getTypeOfLot() {return  typeOfLot;}
    public String getPermit() {return permit;}
    public float getCost() {return cost;}
    public String getTimeOpen(){return timeOpen;}
    public String getTimeClose() {return timeClose;}
    public String getSpecialInfo() {return specialInfo;}
}
