package com.example.smarttourism;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;
import com.example.smarttourism.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadMarkers();
    }

    private void loadMarkers() {
        try {
            InputStream is = getAssets().open("city_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray cityArray = new JSONArray(json);
            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject city = cityArray.getJSONObject(i);
                String name = city.getString("City");
                double lat = city.getDouble("Latitude");
                double lon = city.getDouble("Longitude");

                LatLng location = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(location).title(name));
                if (i == 0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
