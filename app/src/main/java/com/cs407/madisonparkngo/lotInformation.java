package com.cs407.madisonparkngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class lotInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lot_information);


        // Retrieve data from the intent
        String lotName = getIntent().getStringExtra("lotName");
        String lotAddress = getIntent().getStringExtra("lotAddress");
        double lotLat = getIntent().getDoubleExtra("lotLat", 0);
        double lotLong = getIntent().getDoubleExtra("lotLong", 0);
        boolean lotMotorcycle = getIntent().getBooleanExtra("lotMotorcycle", false);
        boolean lotCar = getIntent().getBooleanExtra("lotCar", false);
        boolean lotMoped = getIntent().getBooleanExtra("lotMoped", false);
        String lotTypeOfLot = getIntent().getStringExtra("lotTypeOfLot");
        String lotPermit = getIntent().getStringExtra("lotPermit");
        double lotCost = getIntent().getDoubleExtra("lotCost", 0);
        String lotSpecialInfo = getIntent().getStringExtra("lotSpecialInfo");

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Finish the current activity


        Button takeMeThereButton = findViewById(R.id.buttonTakeMeThere);
        takeMeThereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to MapDestination activity
                Intent intent = new Intent(lotInformation.this, MapDestination.class);
                startActivity(intent);
            }
        });
    }
}
