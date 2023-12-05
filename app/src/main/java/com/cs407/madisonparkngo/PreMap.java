package com.cs407.madisonparkngo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class PreMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premap);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        TextView dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText("Today's Date: " + currentDate);
        EditText destinationEditText = findViewById(R.id.destinationEditText);

        Button button = findViewById(R.id.getNearbyLotsButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SecondActivity
                Intent intent = new Intent(PreMap.this, Map.class);
                startActivity(intent);
            }
        });


        DBHelper dbHelper = DBHelper.getDBInstance(this.getApplicationContext());
        Context context = this.getApplicationContext();
        Button button2 = findViewById(R.id.continueButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = destinationEditText.getText().toString();
                // Start the SecondActivity
                ParkingLot lot = dbHelper.userDao().getSpecificLot(destination);
                if (lot != null) {
                    Intent intent = new Intent(PreMap.this, lotInformation.class);
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
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Parking Lot Does Not Exist in Our Database", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
