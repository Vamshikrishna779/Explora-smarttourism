package com.example.smarttourism;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<PlaceModel> placeList;
    private List<PlaceModel> filteredList;

    public PlaceAdapter(Context context, List<PlaceModel> placeList) {
        this.context = context;
        this.placeList = placeList;
        this.filteredList = new ArrayList<>(placeList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaceModel place = filteredList.get(position);
        holder.placeName.setText(place.getCity());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("City", place.getCity());
            intent.putExtra("Latitude", place.getLatitude());  // ✅ Ensure Latitude is passed
            intent.putExtra("Longitude", place.getLongitude()); // ✅ Ensure Longitude is passed
            intent.putExtra("Ratings", place.getRatings());
            intent.putExtra("IdealDuration", place.getIdealDuration());
            intent.putExtra("BestTime", place.getBestTimeToVisit());
            intent.putExtra("CityDesc", place.getCityDesc());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
        }
    }

    // Search functionality
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<PlaceModel> filteredResults = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredResults.addAll(placeList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (PlaceModel place : placeList) {
                        if (place.getCity().toLowerCase().contains(filterPattern)) {
                            filteredResults.add(place);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredResults;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<PlaceModel>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}


//package com.example.smarttourism;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.smarttourism.R;
//import com.example.smarttourism.DetailsActivity;
//import com.example.smarttourism.PlaceModel;
//import java.util.ArrayList;
//
//public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
//    private Context context;
//    private ArrayList<PlaceModel> placeList;
//
//    public PlaceAdapter(Context context, ArrayList<PlaceModel> placeList) {
//        this.context = context;
//        this.placeList = placeList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_place, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        PlaceModel place = placeList.get(position);
//        holder.nameTextView.setText(place.getName());
//
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DetailsActivity.class);
//            intent.putExtra("name", place.getName());
//            intent.putExtra("latitude", place.getLatitude());
//            intent.putExtra("longitude", place.getLongitude());
//            context.startActivity(intent);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return placeList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView nameTextView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nameTextView = itemView.findViewById(R.id.place_name);
//        }
//    }
//}
