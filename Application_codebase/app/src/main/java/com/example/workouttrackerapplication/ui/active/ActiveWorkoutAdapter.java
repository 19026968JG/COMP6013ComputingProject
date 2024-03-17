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

import com.example.workouttrackerapplication.ActiveWorkoutExerciseNameModel;
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

        // Remove existing nested views
        holder.nestedViewOfSets.removeAllViews();

        // Add nested views dynamically
        for (int i = 0; i < model.getSets(); i++) {
            TextView nestedViewReps = new TextView(context);
            nestedViewReps.setId(View.generateViewId()); // Generate a unique ID for the view
            nestedViewReps.setText("Reps: " + model.getReps());

            TextView nestedViewWeight = new TextView(context);
            nestedViewWeight.setId(View.generateViewId()); // Generate a unique ID for the view
            nestedViewWeight.setText("Weight: " + model.getWeight());

            // Add the nested views to the ConstraintLayout
            holder.nestedViewOfSets.addView(nestedViewReps);
            holder.nestedViewOfSets.addView(nestedViewWeight);

            // Set constraints for nested views
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.nestedViewOfSets);

            // Set constraints for nestedViewReps
            constraintSet.connect(nestedViewReps.getId(), ConstraintSet.TOP, holder.nestedViewOfSets.getId(), ConstraintSet.TOP);
            constraintSet.connect(nestedViewReps.getId(), ConstraintSet.LEFT, holder.nestedViewOfSets.getId(), ConstraintSet.LEFT);
            constraintSet.connect(nestedViewReps.getId(), ConstraintSet.RIGHT, holder.nestedViewOfSets.getId(), ConstraintSet.RIGHT);

            // Set constraints for nestedViewWeight
            constraintSet.connect(nestedViewWeight.getId(), ConstraintSet.TOP, nestedViewReps.getId(), ConstraintSet.BOTTOM);
            constraintSet.connect(nestedViewWeight.getId(), ConstraintSet.LEFT, holder.nestedViewOfSets.getId(), ConstraintSet.LEFT);
            constraintSet.connect(nestedViewWeight.getId(), ConstraintSet.RIGHT, holder.nestedViewOfSets.getId(), ConstraintSet.RIGHT);

            // Apply the constraints
            constraintSet.applyTo(holder.nestedViewOfSets);
        }
    }

    @Override
    public int getItemCount() {
        return activeWorkoutModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView exName;
        private TextView reps;
        private TextView weight;
        private ConstraintLayout nestedViewOfSets;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            exName = itemView.findViewById(R.id.exerciseRowHeader);
            reps = itemView.findViewById(R.id.set_row_reps);
            weight = itemView.findViewById(R.id.set_row_weight);
            // each row of sets
            nestedViewOfSets = itemView.findViewById(R.id.nested_workout_row_layout);
        }
    }

}
