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
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapDestination extends FragmentActivity {
    private GoogleMap mMap;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;
    private static final int DEFAULT_ZOOM = 15;
    LatLng mDestinationLatLng = new LatLng(43.0753,-89.4034);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_destination);

        // Show a Toast message

        // Get destination from intent
//        double destLat = getIntent().getDoubleExtra("DEST_LAT", 0);
//        double destLng = getIntent().getDoubleExtra("DEST_LNG", 0);
//        mDestinationLatLng = new LatLng(destLat, destLng);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            googleMap.addMarker(new MarkerOptions().position(mDestinationLatLng).title("Destination"));
            moveCamera();
            displayMyLocation();

            });
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Finish the current activity

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }
    private void displayMyLocation(){
        int permission= ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }



    };

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&key=" + "AIzaSyBMdEJ0bXnYZoqLxO_eSqT9RNwQVxchVXU";

        // Output format
        String output = "json";

        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    private void moveCamera(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mDestinationLatLng)
                .zoom(11.5f)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
