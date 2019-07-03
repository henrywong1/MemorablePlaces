package com.example.henry.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> locations = new ArrayList<String>();
    static ArrayList<LatLng> savedLocations = new ArrayList<LatLng>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.listView);


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.henry.memorableplaces", Context.MODE_PRIVATE);

        ArrayList<String> latitudes = new ArrayList<String>();
        ArrayList<String> longitudes = new ArrayList<String>();

        locations.clear();
        latitudes.clear();
        longitudes.clear();
        savedLocations.clear();

        try {
            locations = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("locations", ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats", ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (locations.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0) {
            if (locations.size() == latitudes.size() && locations.size() == longitudes.size()) {
                for (int i = 0; i < latitudes.size(); i++) {
                    savedLocations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
        } else {
            locations.add("Add a new location!");
            savedLocations.add(new LatLng(0,0));
        }
        
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, locations);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("locationNum", i);
                startActivity(intent);
            }
        });



    }
}
