//
//public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
//    private List<String> stateList;
//    private Context context;
//    private OnStateClickListener onStateClickListener;
//
//    public interface OnStateClickListener {
//        void onStateClick(String state);
//    }
//
//    public StateAdapter(Context context, List<String> states, OnStateClickListener listener) {
//        this.context = context;
//        this.stateList = states;
//        this.onStateClickListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.state_card, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String state = stateList.get(position);
//        holder.txtStateName.setText(state);
//
//        // Click event to open CityActivity
//        holder.cardView.setOnClickListener(v -> onStateClickListener.onStateClick(state));
//    }
//
//    @Override
//    public int getItemCount() {
//        return stateList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView txtStateName;
//        CardView cardView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            txtStateName = itemView.findViewById(R.id.txtStateName);
//            cardView = itemView.findViewById(R.id.cardViewState);
//        }
//    }
//}
package com.example.smarttourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
    private Context context;
    private List<String> stateList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String state);
    }

    public StateAdapter(Context context, List<String> stateList, OnItemClickListener listener) {
        this.context = context;
        this.stateList = stateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stateName = stateList.get(position);
        holder.stateName.setText(stateName);

        // Convert state name to drawable resource ID
        String imageName = stateName.toLowerCase().replace(" ", "_");
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        if (imageResId != 0) {
            holder.stateImage.setImageResource(imageResId);
        } else {
            holder.stateImage.setImageResource(R.drawable.image2); // Fallback image
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(stateName));
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stateName;
        ImageView stateImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stateName = itemView.findViewById(R.id.stateName);
            stateImage = itemView.findViewById(R.id.stateImage);
        }
    }
}
