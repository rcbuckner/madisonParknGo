package com.cs407.madisonparkngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Listview extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LotAdapter lotAdapter;
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

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize your lotList with data
        lotList = DBHelper.getDBInstance(this.getApplicationContext()).userDao().getAllLots();
        // Add lots to lotList

        lotAdapter = new LotAdapter(this, lotList);
        recyclerView.setAdapter(lotAdapter);


    }
}
