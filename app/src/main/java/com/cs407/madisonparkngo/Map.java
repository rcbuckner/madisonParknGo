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
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.CameraPosition;

import java.util.ArrayList;
import java.util.List;

public class Map extends FragmentActivity {


    private final LatLng mDestinationLatLng = new LatLng(43.0753,-89.4034);
    private LatLng currentLocation = new LatLng(43.0753,-89.4034);
    private GoogleMap mMap;

    private FusedLocationProviderClient mfusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;

    private static final int DEFAULT_ZOOM = 15;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085); // This should be a sensible default for your app

    List<ParkingLot> locationList;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        dbHelper = DBHelper.getDBInstance(this.getApplicationContext());

        locationList = dbHelper.userDao().getAllLots();


        updateMap(null);

        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        Button buttonCost = findViewById(R.id.buttonCost);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonListView = findViewById(R.id.buttonListview);

        Button buttonMotorcycle = findViewById(R.id.buttonMotorcycle);
        buttonMotorcycle.setVisibility(View.GONE);

        Button buttonStreet = findViewById(R.id.buttonStreet);
        buttonStreet.setVisibility(View.GONE);

        Button buttonCostHigh = findViewById(R.id.buttonCostHigh);
        buttonCostHigh.setVisibility(View.GONE);

        Button buttonDestinationHigh = findViewById(R.id.buttonDestinationHigh);
        buttonDestinationHigh.setVisibility(View.GONE);

        Button buttonTypeOfLotTop = findViewById(R.id.buttonTypeOfLotTop);
        buttonTypeOfLotTop.setVisibility(View.GONE);

        Button buttonCostTop = findViewById(R.id.buttonCostTop);
        buttonCostTop.setVisibility(View.GONE);

        Button buttonProximityTop = findViewById(R.id.buttonProximityTop);
        buttonProximityTop.setVisibility(View.GONE);

        Button buttonMoped = findViewById(R.id.buttonMoped);
        buttonMoped.setVisibility(View.GONE);

        Button buttonGarage = findViewById(R.id.buttonGarage);
        buttonGarage.setVisibility(View.GONE);

        Button buttonLowCost = findViewById(R.id.buttonLowCost);
        buttonLowCost.setVisibility(View.GONE);

        Button buttonDestinationSmall = findViewById(R.id.buttonDestinationSmall);
        buttonDestinationSmall.setVisibility(View.GONE);

        Button buttonCar = findViewById(R.id.buttonCar);
        buttonCar.setVisibility(View.GONE);

        Button buttonSurfaceLot = findViewById(R.id.buttonSurfaceLot);
        buttonSurfaceLot.setVisibility(View.GONE);

        Button buttonCostMedium = findViewById(R.id.buttonCostMedium);
        buttonCostMedium.setVisibility(View.GONE);

        Button buttonDestinationMedium = findViewById(R.id.buttonDestinationMedium);
        buttonDestinationMedium.setVisibility(View.GONE);




        // Set onClickListeners
        buttonVehicleType.setOnClickListener(v -> handleVehicleTypeClick());
        buttonLotType.setOnClickListener(v -> handleLotTypeClick());
        buttonCost.setOnClickListener(v -> handleCostClick());
        buttonProximity.setOnClickListener(v -> handleProximityClick());
        buttonBack.setOnClickListener(v -> handleBackClick());
        buttonListView.setOnClickListener(v -> handleListViewClick());

        Button buttonReset = findViewById(R.id.buttonResetFilters);
        buttonReset.setOnClickListener(v -> {
            locationList = dbHelper.userDao().getAllLots();
            updateMap(null);
            Log.i("Reset List Size", "List Size: " + locationList.size());
        });

    }

    private void updateMap(List<ParkingLot> newLot) {
        if (newLot != null) {
            Log.i("Pre Merge Size", "List Size: " + locationList.size());
            locationList = ListHelper.mergeLists(locationList, newLot);

            Log.i("After-Merge Size", "List Size: " + locationList.size());

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;

            googleMap.clear();

            for (ParkingLot location : locationList) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())).title(location.getName()));
            }
            moveCamera();


            displayMyLocation();

            googleMap.setOnMarkerClickListener(marker -> {
                handleMarkerClick(marker);
                return true;

            });

        });

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }
    private void handleVehicleTypeClick() {
        // Get references to the other button
        Button buttonLotType = findViewById(R.id.buttonLotType);
        buttonLotType.setVisibility(View.GONE);
        Button buttonCost = findViewById(R.id.buttonCost);
        buttonCost.setVisibility(View.GONE);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        buttonProximity.setVisibility(View.GONE);

        Button buttonMoped = findViewById(R.id.buttonMoped);
        buttonMoped.setVisibility(View.VISIBLE);
        Button buttonCar = findViewById(R.id.buttonCar);
        buttonCar.setVisibility(View.VISIBLE);
        Button buttonMotorcycle = findViewById(R.id.buttonMotorcycle);
        buttonMotorcycle.setVisibility(View.VISIBLE);

        buttonMoped.setOnClickListener(v -> handleMopedClick());
        buttonCar.setOnClickListener(v -> handleCarClick());
        buttonMotorcycle.setOnClickListener(v -> handleMotorcycleClick());
    }

    private void handleMopedClick() {
        updateMap(dbHelper.userDao().getMopedLots());
        handleBackClick();
    }

    private void handleCarClick() {
        updateMap(dbHelper.userDao().getCarLots());
        handleBackClick();
    }

    private void handleMotorcycleClick() {
        updateMap(dbHelper.userDao().getMotorcycleLots());
        handleBackClick();
    }
    private void handleLotTypeClick() {


        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        buttonVehicleType.setVisibility(View.GONE);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        buttonLotType.setVisibility(View.GONE);
        Button buttonCost = findViewById(R.id.buttonCost);
        buttonCost.setVisibility(View.GONE);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        buttonProximity.setVisibility(View.GONE);

        Button buttonTypeOfLotTop = findViewById(R.id.buttonTypeOfLotTop);
        buttonTypeOfLotTop.setVisibility(View.VISIBLE);
        Button buttonGarage = findViewById(R.id.buttonGarage);
        buttonGarage.setVisibility(View.VISIBLE);
        Button buttonSurfaceLot = findViewById(R.id.buttonSurfaceLot);
        buttonSurfaceLot.setVisibility(View.VISIBLE);
        Button buttonStreet = findViewById(R.id.buttonStreet);
        buttonStreet.setVisibility(View.VISIBLE);

        buttonGarage.setOnClickListener(v -> handleGarageClick());
        buttonStreet.setOnClickListener(v -> handleStreetClick());
        buttonSurfaceLot.setOnClickListener(v -> handleSurfaceClick());
    }

    private void handleGarageClick() {
        updateMap(dbHelper.userDao().getTypeOfLot("Garage"));
        handleBackClick();
    }

    private void handleStreetClick() {
        updateMap(dbHelper.userDao().getTypeOfLot("Street"));
        handleBackClick();
    }

    private void handleSurfaceClick() {
        updateMap(dbHelper.userDao().getTypeOfLot("Surface Lot"));
        handleBackClick();
    }

    private void handleCostClick() {
        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        buttonVehicleType.setVisibility(View.GONE);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        buttonLotType.setVisibility(View.GONE);
        Button buttonCost = findViewById(R.id.buttonCost);
        buttonCost.setVisibility(View.GONE);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        buttonProximity.setVisibility(View.GONE);

        Button buttonCostTop = findViewById(R.id.buttonCostTop);
        buttonCostTop.setVisibility(View.VISIBLE);
        Button buttonLowCost = findViewById(R.id.buttonLowCost);
        buttonLowCost.setVisibility(View.VISIBLE);
        Button buttonCostMedium = findViewById(R.id.buttonCostMedium);
        buttonCostMedium.setVisibility(View.VISIBLE);
        Button buttonCostHigh = findViewById(R.id.buttonCostHigh);
        buttonCostHigh.setVisibility(View.VISIBLE);

        buttonLowCost.setOnClickListener(v -> handleLowCostClick());
        buttonCostMedium.setOnClickListener(v -> handleMediumCostClick());
        buttonCostHigh.setOnClickListener(v -> handleHighCostClick());
    }

    private void handleLowCostClick() {
        updateMap(dbHelper.userDao().getPricedLots(1,0));
        handleBackClick();
    }

    private void handleMediumCostClick() {
        updateMap(dbHelper.userDao().getPricedLots((float)2.01,1));
        handleBackClick();
    }

    private void handleHighCostClick() {
        updateMap(dbHelper.userDao().getPricedLots(100,2));
        handleBackClick();
    }

    private void handleProximityClick() {
        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        buttonVehicleType.setVisibility(View.GONE);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        buttonLotType.setVisibility(View.GONE);
        Button buttonCost = findViewById(R.id.buttonCost);
        buttonCost.setVisibility(View.GONE);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        buttonProximity.setVisibility(View.GONE);

        Button buttonProximityTop = findViewById(R.id.buttonProximityTop);
        buttonProximityTop.setVisibility(View.VISIBLE);
        Button buttonDestinationSmall = findViewById(R.id.buttonDestinationSmall);
        buttonDestinationSmall.setVisibility(View.VISIBLE);
        Button buttonDestinationMedium = findViewById(R.id.buttonDestinationMedium);
        buttonDestinationMedium.setVisibility(View.VISIBLE);
        Button buttonDestinationHigh = findViewById(R.id.buttonDestinationHigh);
        buttonDestinationHigh.setVisibility(View.VISIBLE);

        buttonDestinationSmall.setOnClickListener(v -> handleSmallDesClick());
        buttonDestinationMedium.setOnClickListener(v -> handleMediumDesClick());
        buttonDestinationHigh.setOnClickListener(v -> handleHighDesClick());
    }

    private void handleSmallDesClick() {
        List<ParkingLot> newLots = dbHelper.userDao().getAllLots();

        for(int i = 0; i < newLots.size(); i++) {
            float distance = getDistance((float)currentLocation.latitude, (float) currentLocation.longitude, newLots.get(i).getLatitude(), newLots.get(i).getLongitude());
            if (distance > 0.5) {
                newLots.remove(i);
                i--;
            }
        }
        updateMap(newLots);
        handleBackClick();
    }
    private void handleMediumDesClick() {
        List<ParkingLot> newLots = dbHelper.userDao().getAllLots();

        for(int i = 0; i < newLots.size(); i++) {
            float distance = getDistance((float)currentLocation.latitude, (float) currentLocation.longitude, newLots.get(i).getLatitude(), newLots.get(i).getLongitude());
            if (distance < 0.5 || distance > 1) {
                newLots.remove(i);
                i--;
            }
        }
        updateMap(newLots);
        handleBackClick();
    }
    private void handleHighDesClick(){
        List<ParkingLot> newLots = dbHelper.userDao().getAllLots();

        for(int i = 0; i < newLots.size(); i++) {

            float distance = getDistance((float)currentLocation.latitude, (float) currentLocation.longitude, newLots.get(i).getLatitude(), newLots.get(i).getLongitude());
            if (distance < 1) {
                newLots.remove(i);
                i--;
            }
        }
        updateMap(newLots);
        handleBackClick();
    }

    private void handleBackClick() {

        Button buttonVehicleType = findViewById(R.id.buttonVehicleType);
        buttonVehicleType.setVisibility(View.VISIBLE);
        Button buttonLotType = findViewById(R.id.buttonLotType);
        buttonLotType.setVisibility(View.VISIBLE);
        Button buttonCost = findViewById(R.id.buttonCost);
        buttonCost.setVisibility(View.VISIBLE);
        Button buttonProximity = findViewById(R.id.buttonProximity);
        buttonProximity.setVisibility(View.VISIBLE);

        Button buttonMotorcycle = findViewById(R.id.buttonMotorcycle);
        buttonMotorcycle.setVisibility(View.GONE);

        Button buttonStreet = findViewById(R.id.buttonStreet);
        buttonStreet.setVisibility(View.GONE);

        Button buttonCostHigh = findViewById(R.id.buttonCostHigh);
        buttonCostHigh.setVisibility(View.GONE);

        Button buttonDestinationHigh = findViewById(R.id.buttonDestinationHigh);
        buttonDestinationHigh.setVisibility(View.GONE);

        Button buttonTypeOfLotTop = findViewById(R.id.buttonTypeOfLotTop);
        buttonTypeOfLotTop.setVisibility(View.GONE);

        Button buttonCostTop = findViewById(R.id.buttonCostTop);
        buttonCostTop.setVisibility(View.GONE);

        Button buttonProximityTop = findViewById(R.id.buttonProximityTop);
        buttonProximityTop.setVisibility(View.GONE);

        Button buttonMoped = findViewById(R.id.buttonMoped);
        buttonMoped.setVisibility(View.GONE);

        Button buttonGarage = findViewById(R.id.buttonGarage);
        buttonGarage.setVisibility(View.GONE);

        Button buttonLowCost = findViewById(R.id.buttonLowCost);
        buttonLowCost.setVisibility(View.GONE);

        Button buttonDestinationSmall = findViewById(R.id.buttonDestinationSmall);
        buttonDestinationSmall.setVisibility(View.GONE);

        Button buttonCar = findViewById(R.id.buttonCar);
        buttonCar.setVisibility(View.GONE);

        Button buttonSurfaceLot = findViewById(R.id.buttonSurfaceLot);
        buttonSurfaceLot.setVisibility(View.GONE);

        Button buttonCostMedium = findViewById(R.id.buttonCostMedium);
        buttonCostMedium.setVisibility(View.GONE);

        Button buttonDestinationMedium = findViewById(R.id.buttonDestinationMedium);
        buttonDestinationMedium.setVisibility(View.GONE);

    }

    private void handleListViewClick() {
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
        if (marker.getTitle().equals("Last Known Location")) {
            Toast.makeText(this.getApplicationContext(), "Current Location", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Map.this, lotInformation.class);
            ParkingLot lot = dbHelper.userDao().getSpecificLot(marker.getTitle());
            intent.putExtra("lotName", lot.getName());
            intent.putExtra("lotAddress", lot.getAddress());
            intent.putExtra("lotLat", lot.getLatitude());
            intent.putExtra("lotLong", lot.getLongitude());
            intent.putExtra("lotMotorcycle", lot.allowsMotorcycle());
            intent.putExtra("lotCar", lot.allowsCar());
            intent.putExtra("lotMoped", lot.allowsMoped());
            intent.putExtra("lotTypeOfLot", lot.getTypeOfLot());
            intent.putExtra("lotPermit", lot.getPermit());
            intent.putExtra("lotCost", lot.getCost());
            intent.putExtra("lotSpecialInfo", lot.getSpecialInfo());

            // Add any other marker-specific data you need to pass
            startActivity(intent);
        }
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

                        BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN); // Replace HUE_BLUE with your desired color
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude())).title("Last Known Location").icon(markerIcon));
                        currentLocation = new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
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

    private void moveCamera(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mDestinationLatLng)
                .zoom(13.5f)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private float getDistance(float lat1, float lon1, float lat2, float lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = (R * c * 1000); // convert to meters
        return (float)(distance * 0.000621371);
    }

}