package com.example.smarttourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TopcityAdapter extends RecyclerView.Adapter<TopcityAdapter.ViewHolder> {

    private Context context;
    private List<PlaceModel> placeList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PlaceModel place);
    }

    public TopcityAdapter(Context context, List<PlaceModel> placeList, OnItemClickListener listener) {
        this.context = context;
        this.placeList = placeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topcity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaceModel place = placeList.get(position);
        holder.cityName.setText(place.getCity());
        holder.cityRatings.setText(place.getRatings() + "★ "); // ⭐ Display Ratings


        // ✅ Handle click event
        holder.itemView.setOnClickListener(v -> listener.onItemClick(place));
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView cityRatings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            cityRatings = itemView.findViewById(R.id.cityRatings);
        }
    }
}
