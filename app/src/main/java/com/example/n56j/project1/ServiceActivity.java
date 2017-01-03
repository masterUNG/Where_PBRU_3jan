package com.example.n56j.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private double latPbruADouble = 13.071865; // PBRU location
    private double lngPbruADouble = 99.976742;
    private Button listViewButton, addMarkerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_service_layout);

        //bind
        listViewButton = (Button) findViewById(R.id.button16);
        addMarkerButton = (Button) findViewById(R.id.button15);

        //addMarker
        addMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceActivity.this, AddEditActivity.class);
                intent.putExtra("Status", true);
                startActivity(intent);
            }//onclick
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServiceActivity.this, MyListView.class));
            }
        });


    }//main method


    @Override
    protected void onResume() {
        super.onResume();

        myCreateMarker();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(latPbruADouble, lngPbruADouble);
        // setup pbru center
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        myCreateMarker();


    }//second

    private void myCreateMarker() {

        try {

            mMap.clear();

            SynRoom synRoom = new SynRoom(ServiceActivity.this);
            synRoom.execute();
            String strJSON = synRoom.get();
            Log.d("3janV1", "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);

            String[] nameRoomStrings = new String[jsonArray.length()];
            String[] latStrings = new String[jsonArray.length()];
            String[] lngStrings = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                nameRoomStrings[i] = jsonObject.getString("room_name");
                latStrings[i] = jsonObject.getString("room_lat");
                lngStrings[i] = jsonObject.getString("room_long");

                LatLng latLng = new LatLng(Double.parseDouble(latStrings[i]),
                        Double.parseDouble(lngStrings[i]));
                mMap.addMarker(new MarkerOptions().position(latLng).title(nameRoomStrings[i]));


            }   // for



        } catch (Exception e) {
            e.printStackTrace();
        }

    }   // myCreate

}//main
