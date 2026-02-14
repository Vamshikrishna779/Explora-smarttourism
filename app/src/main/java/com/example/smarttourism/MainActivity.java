package com.example.smarttourism;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
//import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Button;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnStatewise, btnSearch, btnNearbyPlaces, btnMoreFeatures;
    private PlaceAdapter placeAdapter;
    private ArrayList<PlaceModel> placeList;
    private AutoCompleteTextView searchView;
    private List<String> cityNames;
    private List<PlaceModel> filteredList;
    private Runnable autoSlideRunnable;
    private Handler handler = new Handler(Looper.getMainLooper());
    private ViewPager2 viewPager2;
    private List<Drawable> images; // Ensure images is declared globally

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        viewPager2 = findViewById(R.id.trendingCarousel);
        btnStatewise = findViewById(R.id.btnStatewise);
        btnSearch = findViewById(R.id.btnTopCities);
        btnNearbyPlaces = findViewById(R.id.btnBlog);
        btnMoreFeatures = findViewById(R.id.btnMoreFeatures);
        searchView = findViewById(R.id.searchView);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        // Initialize Lists
        placeList = new ArrayList<>();
        cityNames = new ArrayList<>();
        filteredList = new ArrayList<>();
        images = new ArrayList<>(); // Initialize images list

        // Load Images for Carousel
        images.add(getResources().getDrawable(R.drawable.image5));
        images.add(getResources().getDrawable(R.drawable.image2));
        images.add(getResources().getDrawable(R.drawable.image3));
        images.add(getResources().getDrawable(R.drawable.image4));
        images.add(getResources().getDrawable(R.drawable.image1));
        images.add(getResources().getDrawable(R.drawable.image6));

        // Set up ViewPager2 for Carousel
        CarouselAdapter carouselAdapter = new CarouselAdapter(images);
        viewPager2.setAdapter(carouselAdapter);

        // Auto-slide setup
        autoSlideRunnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2 != null) { // Ensure viewPager2 is not null
                    int currentItem = viewPager2.getCurrentItem();
                    int nextItem = (currentItem + 1) % images.size();
                    viewPager2.setCurrentItem(nextItem, true);
//                    handler.postDelayed(this, 3000); // Change slide every 3 seconds
                }
            }
        };

        handler.postDelayed(autoSlideRunnable, 2000);

        // Pause auto-slide when user interacts
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    handler.removeCallbacks(autoSlideRunnable);
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    handler.postDelayed(autoSlideRunnable, 2000);
                }
            }
        });

        // Load JSON Data
        loadJSONData();

        // Set up AutoCompleteTextView
        placeAdapter = new PlaceAdapter(this, filteredList);
        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cityNames);
        searchView.setAdapter(autoCompleteAdapter);

        // Search functionality
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = (String) parent.getItemAtPosition(position);
            openInfoActivity(selectedCity);
        });

        // Button Actions
        btnStatewise.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, StateActivity.class)));
        btnSearch.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TopPlacesActivity.class)));
        btnNearbyPlaces.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BlogActivity.class)));
        btnMoreFeatures.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapsActivity.class)));
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open SettingsActivity when clicked
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoSlideRunnable);
    }

    // Adapter class for ViewPager2 (Carousel)
    public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
        private List<Drawable> imageList;

        public CarouselAdapter(List<Drawable> imageList) {
            this.imageList = imageList;
        }

        @Override
        public CarouselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new CarouselViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(CarouselViewHolder holder, int position) {
            holder.imageView.setImageDrawable(imageList.get(position));
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        public class CarouselViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public CarouselViewHolder(ImageView itemView) {
                super(itemView);
                imageView = itemView;
            }
        }
    }

    private void loadJSONData() {
        try {
            InputStream is = getAssets().open("city_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray cityArray = new JSONArray(json);
//            Log.d("CityCount", "Total cities in JSON: " + cityArray.length());

            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject city = cityArray.getJSONObject(i);
                String name = city.getString("City");
                double lat = city.getDouble("Latitude");
                double lon = city.getDouble("Longitude");
                double ratings = city.getDouble("Ratings");
                String idealDuration = city.getString("Ideal_duration");
                String bestTime = city.getString("Best_time_to_visit");
                String description = city.getString("City_desc");

                PlaceModel place = new PlaceModel(name, lat, lon, ratings, idealDuration, bestTime, description);
                placeList.add(place);
                cityNames.add(name);
            }

            filteredList.addAll(placeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filter(String text) {
        filteredList.clear();
        for (PlaceModel item : placeList) {
            if (item.getCity().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        placeAdapter.notifyDataSetChanged();
    }

    private void openInfoActivity(String cityName) {
        for (PlaceModel item : placeList) {
            if (item.getCity().equalsIgnoreCase(cityName)) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("City", item.getCity());
                intent.putExtra("Latitude", item.getLatitude());
                intent.putExtra("Longitude", item.getLongitude());
                intent.putExtra("Ratings", item.getRatings());
                intent.putExtra("IdealDuration", item.getIdealDuration());
                intent.putExtra("BestTime", item.getBestTimeToVisit());
                intent.putExtra("CityDesc", item.getCityDesc());
                startActivity(intent);
                break;
            }
        }
    }

    public void openMap(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}


//package com.example.smarttourism;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.smarttourism.R;
//import com.example.smarttourism.PlaceAdapter;
//import com.example.smarttourism.PlaceModel;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private PlaceAdapter placeAdapter;
//    private ArrayList<PlaceModel> placeList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        placeList = new ArrayList<>();
//        loadJSONData();
//
//        placeAdapter = new PlaceAdapter(this, placeList);
//        recyclerView.setAdapter(placeAdapter);
//    }
//
//    private void loadJSONData() {
//        try {
//            InputStream is = getAssets().open("city_data.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            String json = new String(buffer, "UTF-8");
//
//            JSONArray cityArray = new JSONArray(json);
//            for (int i = 0; i < cityArray.length(); i++) {
//                JSONObject city = cityArray.getJSONObject(i);
//                String name = city.getString("City");
//                double lat = city.getDouble("Latitude");
//                double lon = city.getDouble("Longitude");
//
//                placeList.add(new PlaceModel(name, lat, lon));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void openMap(View view) {
//        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//        startActivity(intent);
//    }
//}
