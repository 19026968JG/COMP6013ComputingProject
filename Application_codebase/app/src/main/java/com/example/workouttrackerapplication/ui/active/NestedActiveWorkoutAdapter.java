package com.example.workouttrackerapplication.ui.active;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;

import java.util.ArrayList;

public class NestedActiveWorkoutAdapter extends RecyclerView.Adapter<NestedActiveWorkoutAdapter.NestedViewHolder> {
    private Context context;
    private ArrayList<DisplayExerciseObject> exerciseObjects;

    private ArrayList reps;
    private ArrayList weight;

    public NestedActiveWorkoutAdapter(Context context, ArrayList<String> reps, ArrayList<String> weight) {
        this.context = context;
        this.reps = reps;
        this.weight = weight;
    }

    @NonNull
    @Override
    public NestedActiveWorkoutAdapter.NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate((R.layout.active_workout_sets_layout_card),parent,false);
        return new NestedActiveWorkoutAdapter.NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedActiveWorkoutAdapter.NestedViewHolder holder, int position) {
        String numOfReps = reps.get(position).toString();
        String amountOfWeight = weight.get(position).toString();


        // Set sets and reps
            //create a list that is a multiple of exercise models then display that in the child recyclerview

        holder.reps.setText(numOfReps);
        holder.weight.setText(amountOfWeight);
        holder.setNum.setText(Integer.valueOf(position+1).toString());
    }

    @Override
    public int getItemCount() {
        return reps.size();
    }

    public static class NestedViewHolder extends RecyclerView.ViewHolder {
        private TextView reps;
        private TextView weight;
        private TextView setNum;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            reps = itemView.findViewById(R.id.set_row_reps);
            weight = itemView.findViewById(R.id.set_row_weight);
            setNum = itemView.findViewById(R.id.set_row_number);

        }
    }

}
