package com.cs407.madisonparkngo;

public class ParkingLot {
    private String name;
    private String address;
    private int motorcycle;
    private int car;
    private int moped;
    private String typeOfLot;
    private String permit;
    private String cost;
    private String timeOpen;
    private String timeClose;
    private String specialInfo;

    public ParkingLot(String name, String address, int motorcycle, int car, int moped, String typeOfLot, String permit, String cost, String timeOpen, String timeClose, String specialInfo) {
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
    }

    public String getName(){return name;}
    public String getAddress(){return address;}
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
    public String getCost() {return cost;}
    public String getTimeOpen(){return timeOpen;}
    public String getTimeClose() {return timeClose;}
    public String getSpecialInfo() {return specialInfo;}
}
