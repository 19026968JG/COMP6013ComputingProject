package com.example.workouttrackerapplication.ui.active;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;

import java.util.ArrayList;

public class ActiveWorkoutAdapter extends RecyclerView.Adapter<ActiveWorkoutAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ActiveWorkoutExerciseNameModel> activeWorkoutModels;

    public ActiveWorkoutAdapter(Context context, ArrayList<ActiveWorkoutExerciseNameModel> activeWorkoutModels) {
        this.context =context;
        this.activeWorkoutModels = activeWorkoutModels;
    }

    @NonNull
    @Override
    public ActiveWorkoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate((R.layout.active_workout_exercise),parent,false);
        return new ActiveWorkoutAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ActiveWorkoutExerciseNameModel model = activeWorkoutModels.get(position);

        // TODO Create another recycler view that output the sets data for each row

        // Set exercise name
        holder.exName.setText(model.getExerciseName());
        holder.reps.setText(model.getReps());
        holder.weight.setText(model.getWeight());

    }

    @Override
    public int getItemCount() {
        return activeWorkoutModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView exName;
        private TextView reps;
        private TextView weight;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            exName = itemView.findViewById(R.id.exerciseRowHeader);
            reps = itemView.findViewById(R.id.set_row_reps);
            weight =itemView.findViewById(R.id.set_row_weight);

        }
    }

}
