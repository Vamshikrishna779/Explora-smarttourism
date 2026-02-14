package com.example.smarttourism;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class InfoActivity extends AppCompatActivity {
    private TextView cityName, cityRatings, idealDuration, bestTime, cityDesc;
    private Button exploreButton;
    private String name, duration, bestVisitTime, description;
    private double ratings, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        cityName = findViewById(R.id.cityName);
        cityRatings = findViewById(R.id.cityRatings);
        idealDuration = findViewById(R.id.idealDuration);
        bestTime = findViewById(R.id.bestTime);
        cityDesc = findViewById(R.id.cityDesc);
        exploreButton = findViewById(R.id.exploreButton);

        // Receiving data from MainActivity
        Intent intent = getIntent();
        name = intent.getStringExtra("City");
        ratings = intent.getDoubleExtra("Ratings", 0.0);
        duration = intent.getStringExtra("IdealDuration");
        bestVisitTime = intent.getStringExtra("BestTime");
        description = intent.getStringExtra("CityDesc");
        latitude = intent.getDoubleExtra("Latitude", 0.0);
        longitude = intent.getDoubleExtra("Longitude", 0.0);

        // Debugging: Check if data is received
//        Log.d("DEBUG", "InfoActivity Received: " + name + " Lat: " + latitude + " Lon: " + longitude);

        // Setting values
        cityName.setText(name);
        cityRatings.setText("Ratings: " + ratings+"⭐ ");
        idealDuration.setText("Ideal Duration: " + duration + " days");
        bestTime.setText("Best Time to Visit: " + bestVisitTime);

        // Clean the description data
        description = description.replace("[", "").replace("]", "") // Remove square brackets
                .replace("\\", "") // Remove backslashes (escaped characters)
                .replace("\u2013", "-") // Replace em dash with regular dash
                .replace("''", "") // Remove empty strings
                .trim();

        // Split the description into points
        String[] descPoints = description.split(", '");

        // Format description as bullet points
        StringBuilder formattedDesc = new StringBuilder();
        for (String point : descPoints) {
            point = point.replace("'", "").trim(); // Remove single quotes and trim
            if (!point.isEmpty()) {
                formattedDesc.append("\u2022 ").append(point).append("\n\n");
            }
        }

        cityDesc.setText(formattedDesc.toString().trim());

        exploreButton.setOnClickListener(v -> {
            Intent detailsIntent = new Intent(InfoActivity.this, DetailsActivity.class);
            detailsIntent.putExtra("City", name);
            detailsIntent.putExtra("Latitude", latitude);
            detailsIntent.putExtra("Longitude", longitude);
            startActivity(detailsIntent);
        });
    }

}
