//package com.example.smarttourism;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import androidx.appcompat.app.AppCompatActivity;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashSet;
//
//public class StateActivity extends AppCompatActivity {
//    private ListView listView;
//    private ArrayList<String> stateList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_state);
//
//        listView = findViewById(R.id.listViewStates);
//        stateList = new ArrayList<>();
//
//        loadJSONData();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stateList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedState = stateList.get(position);
//            Intent intent = new Intent(StateActivity.this, CityActivity.class);
//            intent.putExtra("SelectedState", selectedState);
//            startActivity(intent);
//        });
//    }
//
//    private void loadJSONData() {
//        try {
//            InputStream is = getAssets().open("place_data.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            String json = new String(buffer, "UTF-8");
//
//            JSONArray placeArray = new JSONArray(json);
//            HashSet<String> uniqueStates = new HashSet<>();
//            for (int i = 0; i < placeArray.length(); i++) {
//                JSONObject placeObject = placeArray.getJSONObject(i);
//                uniqueStates.add(placeObject.getString("State"));
//            }
//            stateList.addAll(uniqueStates);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
package com.example.smarttourism;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class StateActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StateAdapter adapter;
    private List<String> stateList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewStates);
        stateList = new ArrayList<>();

        loadJSONData();

        // Sort states in ascending order
        Collections.sort(stateList);

        // Set up RecyclerView with GridLayout (2 columns)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new StateAdapter(this, stateList, state -> {
            // Open CityActivity when a state is clicked
            Intent intent = new Intent(StateActivity.this, CityActivity.class);
            intent.putExtra("SelectedState", state);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    private void loadJSONData() {
        try {
            InputStream is = getAssets().open("place_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray placeArray = new JSONArray(json);
            HashSet<String> uniqueStates = new HashSet<>();
            for (int i = 0; i < placeArray.length(); i++) {
                JSONObject placeObject = placeArray.getJSONObject(i);
                uniqueStates.add(placeObject.getString("State"));
            }
            stateList.addAll(uniqueStates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
