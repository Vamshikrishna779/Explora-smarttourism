package com.example.smarttourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> displayList;
    private HashMap<String, JSONObject> placeDetailsMap;
    private OnCityClickListener onCityClickListener;

    // Constructor
    public CityAdapter(Context context, ArrayList<String> displayList, HashMap<String, JSONObject> placeDetailsMap, OnCityClickListener listener) {
        this.context = context;
        this.displayList = displayList;
        this.placeDetailsMap = placeDetailsMap;
        this.onCityClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String cityPlace = displayList.get(position);
        holder.cityPlaceTextView.setText(cityPlace);

        // Set the click listener to call the method in CityActivity
        holder.itemView.setOnClickListener(v -> {
            // Pass the selected item (City - Place) as a String
            onCityClickListener.onCityClick(cityPlace);
        });
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    // ViewHolder class for holding the views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityPlaceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cityPlaceTextView = itemView.findViewById(R.id.txtCityName); // Assuming this TextView ID exists in your layout
        }
    }

    // Interface for handling item click
    public interface OnCityClickListener {
        void onCityClick(String cityPlace);
    }
}
