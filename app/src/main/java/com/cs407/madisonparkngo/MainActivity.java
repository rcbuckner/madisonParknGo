package com.cs407.madisonparkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        csvParser.parse(this.getApplicationContext());
        Log.i("Test DB Creation", "DB at first lotID: " +
                DBHelper.getDBInstance(this.getApplicationContext()).userDao().getAllLots().get(0).getName());
    }
}