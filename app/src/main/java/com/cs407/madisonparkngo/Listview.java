package com.cs407.madisonparkngo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Listview extends AppCompatActivity {

    private ListView listView;
    private List<ParkingLot> lotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        Button buttonMapview = findViewById(R.id.buttonMapview);
        buttonMapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to Map activity
                Intent intent = new Intent(Listview.this, Map.class);
                startActivity(intent);
            }
        });

        // Initialize your lotList with data
        lotList = DBHelper.getDBInstance(this.getApplicationContext()).userDao().getAllLots();
        // Add lots to lotList

        ArrayList<String> parkingLots = new ArrayList<>();

        for (ParkingLot lot : lotList) {
            parkingLots.add(lot.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,parkingLots);

        listView = (ListView) findViewById(R.id.locationListView);
        listView.setAdapter(adapter);

        DBHelper dbHelper = DBHelper.getDBInstance(this.getApplicationContext());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ParkingLot lot = dbHelper.userDao().getSpecificLot(listView.getItemAtPosition(i).toString());
                Intent intent = new Intent(getApplicationContext(), lotInformation.class);
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
            }
        });
    }
}
