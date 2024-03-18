package com.example.workouttrackerapplication.ui.active;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;

import java.util.ArrayList;

public class NestedActiveWorkoutAdapter extends RecyclerView.Adapter<NestedActiveWorkoutAdapter.NestedViewHolder> {
    private ArrayList<String> weightRepsList;
    public NestedActiveWorkoutAdapter(ArrayList<String> weightRepsList) {
        this.weightRepsList = weightRepsList;
    }

    @NonNull
    @Override
    public NestedActiveWorkoutAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.id.nested_workout_row_layout,false);
        return NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedActiveWorkoutAdapter.NestedViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NestedViewHolder extends RecyclerView.ViewHolder {
        TextView reps;
        TextView weight;


        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            reps = itemView.findViewById(R.id.set_row_reps);
            weight = itemView.findViewById(R.id.set_row_weight);
        }
    }

}
