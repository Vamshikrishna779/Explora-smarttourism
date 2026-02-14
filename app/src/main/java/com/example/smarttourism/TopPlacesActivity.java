package com.example.smarttourism;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TopPlacesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopcityAdapter topcityAdapter;
    private ArrayList<PlaceModel> placeList;
    private List<String> cityNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_places);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewCities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        placeList = new ArrayList<>();
        cityNames = new ArrayList<>();
        loadJSONData();

        // ✅ Adapter now includes ClickListener
        topcityAdapter = new TopcityAdapter(this, placeList, this::openInfoActivity);
        recyclerView.setAdapter(topcityAdapter);
    }

    // 🔥 Open InfoActivity when a city is clicked
    private void openInfoActivity(PlaceModel place) {
//        Log.d("DEBUG", "Sending: " + place.getCity() + " Lat: " + place.getLatitude() + " Lon: " + place.getLongitude());
        Intent intent = new Intent(TopPlacesActivity.this, InfoActivity.class);
        intent.putExtra("City", place.getCity());
        intent.putExtra("Latitude", place.getLatitude());
        intent.putExtra("Longitude", place.getLongitude());
        intent.putExtra("Ratings", place.getRatings());
        intent.putExtra("IdealDuration", place.getIdealDuration());
        intent.putExtra("BestTime", place.getBestTimeToVisit());
        intent.putExtra("CityDesc", place.getCityDesc());
        startActivity(intent);
    }

    private void loadJSONData() {
        try {
            InputStream is = getAssets().open("city_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray cityArray = new JSONArray(json);
//            Log.d("CityCount", "Total cities in JSON: " + cityArray.length());

            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject cityObj = cityArray.getJSONObject(i);
                String name = cityObj.getString("City");
                double lat = cityObj.getDouble("Latitude");
                double lon = cityObj.getDouble("Longitude");
                double ratings = cityObj.getDouble("Ratings");
                String idealDuration = cityObj.getString("Ideal_duration");
                String bestTime = cityObj.getString("Best_time_to_visit");
                String description = cityObj.getString("City_desc");

                PlaceModel place = new PlaceModel(name, lat, lon, ratings, idealDuration, bestTime, description);
                placeList.add(place);
                cityNames.add(name);
            }

//            Log.d("CityCount", "Total cities added to list: " + placeList.size());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading city data.", Toast.LENGTH_SHORT).show();
        }
    }
}
