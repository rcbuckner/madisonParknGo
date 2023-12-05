package com.cs407.madisonparkngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class lotInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lot_information);


        // Retrieve data from the intent
//        String lotName = getIntent().getStringExtra("lotName");
//        String lotAddress = getIntent().getStringExtra("lotAddress");
        float lotLat = getIntent().getFloatExtra("lotLat", (float)39.0738);
        float lotLong = getIntent().getFloatExtra("lotLong", (float)125.8198);
//        boolean lotMotorcycle = getIntent().getBooleanExtra("lotMotorcycle", false);
//        boolean lotCar = getIntent().getBooleanExtra("lotCar", false);
//        boolean lotMoped = getIntent().getBooleanExtra("lotMoped", false);
//        String lotTypeOfLot = getIntent().getStringExtra("lotTypeOfLot");
//        String lotPermit = getIntent().getStringExtra("lotPermit");
//        float lotCost = getIntent().getFloatExtra("lotCost", 0);



        String lotVehicles = buildVehicleString(
                getIntent().getBooleanExtra("lotMotorcycle", false),
                getIntent().getBooleanExtra("lotCar", false),
                getIntent().getBooleanExtra("lotMoped", false));
        String lotAddress = getIntent().getStringExtra("lotAddress");
        String lotTypeOfLot = getIntent().getStringExtra("lotTypeOfLot");
        String lotPermit = getIntent().getStringExtra("lotPermit");
        String lotName = getIntent().getStringExtra("lotName");
        String lotSpecialInfo = getIntent().getStringExtra("lotSpecialInfo");
        Float lotCost = DBHelper.getDBInstance(this.getApplicationContext()).userDao().getSpecificLot(lotName).getCost();

        // Find the TextViews by their IDs and update their text
        TextView textCost = findViewById(R.id.textCost);
        TextView textVehicles = findViewById(R.id.textVehicles);
        TextView textAddress = findViewById(R.id.textAddress);
        TextView textLotType = findViewById(R.id.textLotType);
        TextView textPermit = findViewById(R.id.textPermit);
        TextView textTitle = findViewById(R.id.lotTitle);
        TextView textInfo = findViewById(R.id.textInfo);

        textCost.setText("Cost: " + (lotCost != -1 ? "$" + String.format("%.2f", lotCost) + " per hour" : "Not available"));
        textVehicles.setText("Vehicles: " + lotVehicles);
        textAddress.setText("Address: " + (lotAddress != null ? lotAddress : "Not available"));
        textLotType.setText("Lot Type: " + (lotTypeOfLot != null ? lotTypeOfLot : "Not available"));
        textPermit.setText("Permit: " + (lotPermit != null ? lotPermit : "Not available"));
        textTitle.setText(""+lotName);
        textInfo.setText("Info: "+ (lotSpecialInfo != null ? lotSpecialInfo : "Not available"));



        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Finish the current activity


        Button takeMeThereButton = findViewById(R.id.buttonTakeMeThere);
        takeMeThereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to MapDestination activity
                Intent intent = new Intent(lotInformation.this, MapDestination.class);
                intent.putExtra("lotLat", lotLat);
                intent.putExtra("lotLong", lotLong);
                intent.putExtra("lotName", lotName);
                startActivity(intent);
            }
        });
    }

    private String buildVehicleString(boolean motorcycle, boolean car, boolean moped) {
        StringBuilder vehicles = new StringBuilder();
        if (motorcycle) {
            vehicles.append("Motorcycle ");
        }
        if (car) {
            vehicles.append("Car ");
        }
        if (moped) {
            vehicles.append("Moped");
        }
        return vehicles.length() > 0 ? vehicles.toString() : "Not available";
    }

}
