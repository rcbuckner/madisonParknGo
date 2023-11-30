package com.cs407.madisonparkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_screen);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SecondActivity
                Intent intent = new Intent(MainActivity.this, PreMap.class);
                startActivity(intent);
            }
        });

        setContentView(R.layout.activity_main);

        if (DBHelper.getDBInstance(this.getApplicationContext()).userDao().getAllLots().size() == 0) {
            csvParser.parse(this.getApplicationContext());
        }
        Log.i("Test DB Creation", "DB Size: " +
                DBHelper.getDBInstance(this.getApplicationContext()).userDao().getAllLots().size());
    }
}