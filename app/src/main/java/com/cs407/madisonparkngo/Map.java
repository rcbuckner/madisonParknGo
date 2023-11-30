package com.cs407.madisonparkngo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.ArrayList;
import java.util.List;

public class Map extends FragmentActivity {

    private final LatLng mDestinationLatLng = new LatLng(43.0753,-89.4034);
    private GoogleMap mMap;

    private FusedLocationProviderClient mfusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;

    private static final int DEFAULT_ZOOM = 15;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085); // This should be a sensible default for your app

    List<ParkingLot> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        locationList = DBHelper.getDBInstance(this.getApplicationContext()).userDao().getAllLots();

        updateMap(null, getApplicationContext());

        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonListView = findViewById(R.id.buttonListview);

        // Set onClickListeners
        buttonVehicleType.setOnClickListener(v -> handleVehicleTypeClick(this.getApplicationContext()));
        buttonLotType.setOnClickListener(v -> handleLotTypeClick(this.getApplicationContext()));
        buttonCost.setOnClickListener(v -> handleCostClick(this.getApplicationContext()));
        buttonProximity.setOnClickListener(v -> handleProximityClick(this.getApplicationContext()));
        buttonBack.setOnClickListener(v -> handleBackClick(this.getApplicationContext()));
        buttonListView.setOnClickListener(v -> handleListViewClick(this.getApplicationContext()));




    }

    private void updateMap(List<ParkingLot> newLot, Context context) {
        if (newLot != null) {
            ListHelper.mergeLists(locationList, newLot);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;

            // Loop through the list and add a marker for each location
            for (ParkingLot location : locationList) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title(location.getName()));
            }


            googleMap.addMarker(new MarkerOptions().position(mDestinationLatLng).title("Destination"));
            displayMyLocation();

            googleMap.setOnMarkerClickListener(marker -> {
                handleMarkerClick(marker);
                return true;

            });

        });

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }
    private void handleVehicleTypeClick(Context context) {
        // Get references to the other buttons
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Change the texts of the buttons
        buttonLotType.setText("Moped");
        buttonCost.setText("Car");
        buttonProximity.setText("Motorcycle");

        // Set new onClickListeners for the buttons to define their new actions
        //NOTDEFINED YET
        buttonLotType.setOnClickListener(v -> {
            updateMap(DBHelper.getDBInstance(context).userDao().getMopedLots(), context);
        });
//        buttonCost.setOnClickListener(v -> handleCarClick());
//        buttonProximity.setOnClickListener(v -> handleMotorcycleClick());
        // Keep the back button's functionality as is, or modify if needed
    }

    private void handleLotTypeClick(Context context) {

        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);

        // Change the texts of the buttons
        buttonVehicleType.setText("Lot Type");
        buttonLotType.setText("Garage");
        buttonCost.setText("Surface Lot");
        buttonProximity.setText("Street");


    }

    private void handleCostClick(Context context) {
        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);

        // Change the texts of the buttons
        buttonVehicleType.setText("Cost");
        buttonLotType.setText("$0-$1");
        buttonCost.setText("$1-$2");
        buttonProximity.setText("More Than $2");
    }

    private void handleProximityClick(Context context) {
        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);

        // Change the texts of the buttons
        buttonVehicleType.setText("Proximity to Destination");
        buttonLotType.setText("0 - 0.5 Miles");
        buttonCost.setText("0.5 - 1 Miles");
        buttonProximity.setText("More Than 1 Miles");
    }

    private void handleBackClick(Context context) {

        // Reset the onClickListeners for the buttons
        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);

        buttonVehicleType.setText("Vehicle Type");
        buttonLotType.setText("Type of Lot");
        buttonCost.setText("Cost");
        buttonProximity.setText("Proximity to Destination");


        buttonVehicleType.setOnClickListener(v -> handleVehicleTypeClick(context));
        buttonLotType.setOnClickListener(v -> handleLotTypeClick(context));
        buttonCost.setOnClickListener(v -> handleCostClick(context));
        buttonProximity.setOnClickListener(v -> handleProximityClick(context));
    }

    private void handleListViewClick(Context context) {
        Button buttonListView = findViewById(R.id.buttonListview);
        buttonListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SecondActivity
                Intent intent = new Intent(Map.this, Listview.class);
                startActivity(intent);
            }
        });

    }


    private void handleMarkerClick(Marker marker) {
        // Start the LotInformation activity
        Intent intent = new Intent(Map.this, lotInformation.class);
        intent.putExtra("markerTitle", marker.getTitle());
        intent.putExtra("markerLat", marker.getPosition().latitude);
        intent.putExtra("markerLng", marker.getPosition().longitude);
        // Add any other marker-specific data you need to pass
        startActivity(intent);
    }





    private void displayMyLocation(){
        int permission= ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
        else {
            mfusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, task ->{
                Location mLastKnownLocation = task.getResult();
                if (task.isSuccessful() && mLastKnownLocation != null){

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
                    mapFragment.getMapAsync(googleMap -> {
                        mMap = googleMap;
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude())).title("Last Known Location"));
                        displayMyLocation();
                    });
                }



            });

        }


    };

    @Override
    public void onRequestPermissionsResult(int resultCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(resultCode, permissions, grantResults);
        if (resultCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayMyLocation();
            }
        }
    }



}