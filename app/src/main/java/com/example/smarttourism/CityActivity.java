package com.example.smarttourism;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class CityActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCities;
    private CityAdapter cityAdapter;

    private ArrayList<String> displayList;  // List with City - Place format
    private HashMap<String, JSONObject> placeDetailsMap;
    private String selectedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        // Initialize RecyclerView
        recyclerViewCities = findViewById(R.id.recyclerViewCities);
        recyclerViewCities.setLayoutManager(new GridLayoutManager(this, 2));  // 2 columns for the grid

        displayList = new ArrayList<>();
        placeDetailsMap = new HashMap<>();

        // Get selected state from intent
        selectedState = getIntent().getStringExtra("SelectedState");
        // Set the dynamic title
        TextView txtExploreState = findViewById(R.id.txtExploreState);
        if (selectedState != null) {
            txtExploreState.setText("Explore " + selectedState);
        }
        // Load place data from JSON
        loadJSONData();

        // Initialize and set the adapter for the RecyclerView
        cityAdapter = new CityAdapter(this, displayList, placeDetailsMap, this::openCityDetails);
        recyclerViewCities.setAdapter(cityAdapter);
    }

    // Method to open the city details activity when an item is clicked
    private void openCityDetails(String selectedItem) {
        JSONObject placeDetails = placeDetailsMap.get(selectedItem);

        if (placeDetails != null) {
            Intent intent = new Intent(CityActivity.this, OverviewActivity.class);
            intent.putExtra("PlaceDetails", placeDetails.toString());
            try {
                intent.putExtra("Name", placeDetails.getString("Name"));
                intent.putExtra("Type", placeDetails.optString("Type", "N/A"));
                intent.putExtra("Establishment_Year", placeDetails.optString("Establishment Year", "Unknown"));
                intent.putExtra("Time_Needed", placeDetails.optDouble("time needed to visit in hrs", 0.0));
                intent.putExtra("Google_Review_Rating", placeDetails.optDouble("Google review rating", 0.0));
                intent.putExtra("Entrance_Fee", placeDetails.optInt("Entrance Fee in INR", 0));
                intent.putExtra("Airport_Nearby", placeDetails.optString("Airport with 50km Radius", "Not Available"));
                intent.putExtra("Weekly_Off", placeDetails.optString("Weekly Off", "N/A"));
                intent.putExtra("Significance", placeDetails.optString("Significance", "No information"));
                intent.putExtra("DSLR_Allowed", placeDetails.optString("DSLR Allowed", "Unknown"));
                intent.putExtra("Number_of_Reviews", placeDetails.optDouble("Number of google review in lakhs", 0.0));
                intent.putExtra("Best_Time", placeDetails.optString("Best Time to visit", "Anytime"));

                // Pass Lat-Long for Map
                if (placeDetails.has("Latitude") && placeDetails.has("Longitude")) {
                    intent.putExtra("Latitude", placeDetails.optDouble("Latitude", 0.0));
                    intent.putExtra("Longitude", placeDetails.optDouble("Longitude", 0.0));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading place details.", Toast.LENGTH_SHORT).show();
            }

            startActivity(intent);
        }
    }

    // Method to load place data from JSON file
    private void loadJSONData() {
        try (InputStream is = getAssets().open("place_data.json")) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray placeArray = new JSONArray(json);
            for (int i = 0; i < placeArray.length(); i++) {
                JSONObject placeObject = placeArray.getJSONObject(i);
                if (placeObject.getString("State").equalsIgnoreCase(selectedState)) {
                    String city = placeObject.getString("City");
                    String placeName = placeObject.getString("Name");
                    String displayText = city + " - " + placeName; // Example: "Delhi - India Gate"

                    displayList.add(displayText);
                    placeDetailsMap.put(displayText, placeObject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading data.", Toast.LENGTH_SHORT).show();
        }
    }
}
