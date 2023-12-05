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

        // Get data from intent
        String markerTitle = getIntent().getStringExtra("markerTitle");
        double markerLat = getIntent().getDoubleExtra("markerLat", 0);
        double markerLng = getIntent().getDoubleExtra("markerLng", 0);

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
