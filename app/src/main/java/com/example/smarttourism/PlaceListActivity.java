//package com.example.smarttourism;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import androidx.appcompat.app.AppCompatActivity;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//public class PlaceListActivity extends AppCompatActivity {
//    private ListView listView;
//    private ArrayList<String> placeList;
//    private String selectedCity;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_place_list);
//
//        listView = findViewById(R.id.listViewPlaces);
//        placeList = new ArrayList<>();
//        selectedCity = getIntent().getStringExtra("SelectedCity");
//
//        loadJSONData();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placeList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedPlace = placeList.get(position);
//            Intent intent = new Intent(PlaceListActivity.this, PlaceOverviewActivity.class);
//            intent.putExtra("SelectedPlace", selectedPlace);
//            startActivity(intent);
//        });
//    }
//
//    private void loadJSONData() {
//        try {
//            InputStream is = getAssets().open("places_data.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            String json = new String(buffer, "UTF-8");
//
//            JSONArray placeArray = new JSONArray(json);
//            for (int i = 0; i < placeArray.length(); i++) {
//                JSONObject placeObject = placeArray.getJSONObject(i);
//                if (placeObject.getString("City").equals(selectedCity)) {
//                    placeList.add(placeObject.getString("Name"));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
