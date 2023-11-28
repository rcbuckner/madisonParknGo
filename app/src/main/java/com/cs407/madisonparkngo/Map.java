package com.cs407.madisonparkngo;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Map extends FragmentActivity {

    private final LatLng mDestinationLatLng = new LatLng(43.0753,-89.4034);
    private GoogleMap mMap;

    private FusedLocationProviderClient mfusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;

    private static final int DEFAULT_ZOOM = 15;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085); // This should be a sensible default for your app


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(googleMap -> {
                    mMap = googleMap;
                    googleMap.addMarker(new MarkerOptions().position(mDestinationLatLng).title("Destination"));
                    displayMyLocation();

                    googleMap.setOnMarkerClickListener(marker -> {
                        handleMarkerClick();
                        return true;
                    });
                });

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Set onClickListeners
        buttonVehicleType.setOnClickListener(v -> handleVehicleTypeClick());
        buttonLotType.setOnClickListener(v -> handleLotTypeClick());
        buttonCost.setOnClickListener(v -> handleCostClick());
        buttonProximity.setOnClickListener(v -> handleProximityClick());
        buttonBack.setOnClickListener(v -> handleBackClick());

    }

    private void handleVehicleTypeClick() {
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
//        buttonLotType.setOnClickListener(v -> handleMopedClick());
//        buttonCost.setOnClickListener(v -> handleCarClick());
//        buttonProximity.setOnClickListener(v -> handleMotorcycleClick());
        // Keep the back button's functionality as is, or modify if needed
    }

    private void handleLotTypeClick() {

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

    private void handleCostClick() {
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

    private void handleProximityClick() {
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

    private void handleBackClick() {

        // Reset the onClickListeners for the buttons
        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);

        buttonVehicleType.setText("Vehicle Type");
        buttonLotType.setText("Lot Type");
        buttonCost.setText("Cost");
        buttonProximity.setText("Proximity to Destination");


        buttonVehicleType.setOnClickListener(v -> handleVehicleTypeClick());
        buttonLotType.setOnClickListener(v -> handleLotTypeClick());
        buttonCost.setOnClickListener(v -> handleCostClick());
        buttonProximity.setOnClickListener(v -> handleProximityClick());
    }


    private void handleMarkerClick() {
        // Start the lotInformation activity
        Intent intent = new Intent(Map.this, lotInformation.class);
        //intent.putExtra("markerTitle", clickedMarker.getTitle());
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
                    mMap.addPolyline(new PolylineOptions().add(
                            new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()), mDestinationLatLng));

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