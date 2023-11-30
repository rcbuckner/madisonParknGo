package com.cs407.madisonparkngo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (DBHelper.getDBInstance(this.getApplicationContext()).userDao().getAllLots().size() == 0) {
            csvParser.parse(this.getApplicationContext());
        }
        Log.i("Test DB Creation", "DB at first lotID: " +
                DBHelper.getDBInstance(this.getApplicationContext()).userDao().getAllLots().size());
    }
}