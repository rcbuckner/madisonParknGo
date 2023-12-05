package com.cs407.madisonparkngo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
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



        Button button2 = findViewById(R.id.continueButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = destinationEditText.getText().toString();
                // Start the SecondActivity
                Intent intent = new Intent(PreMap.this, Map.class);
                startActivity(intent);
            }
        });


    }
}
