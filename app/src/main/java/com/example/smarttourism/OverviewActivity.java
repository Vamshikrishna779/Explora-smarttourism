package com.example.smarttourism;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

public class OverviewActivity extends AppCompatActivity {
    private TextView txtPlaceName, txtType, txtYear, txtTimeNeeded, txtRating, txtFee, txtAirport,
            txtWeeklyOff, txtSignificance, txtDSLR, txtReviewCount, txtBestTime;
    private Button btnViewOnMap;
    private double latitude, longitude;
    private boolean hasLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        // Initialize TextViews
        txtPlaceName = findViewById(R.id.txtPlaceName);
        txtType = findViewById(R.id.txtType);
        txtYear = findViewById(R.id.txtYear);
        txtTimeNeeded = findViewById(R.id.txtTimeNeeded);
        txtRating = findViewById(R.id.txtRating);
        txtFee = findViewById(R.id.txtFee);
        txtAirport = findViewById(R.id.txtAirport);
        txtWeeklyOff = findViewById(R.id.txtWeeklyOff);
        txtSignificance = findViewById(R.id.txtSignificance);
        txtDSLR = findViewById(R.id.txtDSLR);
        txtReviewCount = findViewById(R.id.txtReviewCount);
        txtBestTime = findViewById(R.id.txtBestTime);
        btnViewOnMap = findViewById(R.id.btnViewOnMap);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent.hasExtra("PlaceDetails")) {
            try {
                JSONObject placeDetails = new JSONObject(intent.getStringExtra("PlaceDetails"));

                txtPlaceName.setText(placeDetails.optString("Name", "Unknown"));
                txtType.setText("Type: " + placeDetails.optString("Type", "N/A"));
                txtYear.setText("Establishment Year: " + placeDetails.optString("Establishment Year", "N/A"));
                txtTimeNeeded.setText("Time Needed: " + placeDetails.optDouble("time needed to visit in hrs", 0.0) + " hrs");
                txtRating.setText(String.format("Rating: ⭐ %.1f", placeDetails.optDouble("Google review rating", 0.0)));
                txtFee.setText("Entrance Fee: ₹" + placeDetails.optInt("Entrance Fee in INR", 0));
                txtAirport.setText("Airport Nearby: " + placeDetails.optString("Airport with 50km Radius", "N/A"));
                txtWeeklyOff.setText("Weekly Off: " + (placeDetails.isNull("Weekly Off") ? "None" : placeDetails.optString("Weekly Off")));
                txtSignificance.setText("Significance: " + placeDetails.optString("Significance", "N/A"));
                txtDSLR.setText("DSLR Allowed: " + placeDetails.optString("DSLR Allowed", "Unknown"));
                txtReviewCount.setText(String.format("Reviews: %.1f Lakh", placeDetails.optDouble("Number of google review in lakhs", 0.0)));
                txtBestTime.setText("Best Time to Visit: " + placeDetails.optString("Best Time to visit", "Anytime"));

                // Get Lat-Long for Map
                if (!placeDetails.isNull("Latitude") && !placeDetails.isNull("Longitude")) {
                    latitude = placeDetails.optDouble("Latitude", 0.0);
                    longitude = placeDetails.optDouble("Longitude", 0.0);
                    hasLocation = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Handle Map Button Click
        if (hasLocation) {
            btnViewOnMap.setVisibility(View.VISIBLE);
            String placeName = txtPlaceName.getText().toString(); // Get place name
            btnViewOnMap.setOnClickListener(v -> openGoogleMaps(latitude, longitude, placeName));
        } else {
            btnViewOnMap.setVisibility(View.GONE);
        }

    }

    // Open Google Maps with the given coordinates
    // Open Google Maps with the given coordinates and place name
    private void openGoogleMaps(double lat, double lng, String placeName) {
        String uri = "geo:" + lat + "," + lng + "?q=" + Uri.encode(placeName);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}
