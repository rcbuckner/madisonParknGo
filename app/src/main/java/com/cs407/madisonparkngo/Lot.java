package com.cs407.madisonparkngo;

public class Lot {
    private String name;
    private String location;
    private int capacity;
    private double price;

    // Constructor
    public Lot(String name, String location, int capacity, double price) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.price = price;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    // ... (if you need to modify the properties)
}

