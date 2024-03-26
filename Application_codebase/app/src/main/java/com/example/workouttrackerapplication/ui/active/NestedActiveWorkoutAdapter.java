package com.example.workouttrackerapplication.ui.active;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;

import java.util.ArrayList;

public class NestedActiveWorkoutAdapter extends RecyclerView.Adapter<NestedActiveWorkoutAdapter.NestedViewHolder> {
    private Context context;
    private ArrayList<ActiveWorkoutExerciseModel> exerciseObject;
    private CompletedSetsAction listener;
    private DatabaseSavedWorkouts db;


    public NestedActiveWorkoutAdapter(Context context, ArrayList<ActiveWorkoutExerciseModel> exerciseObject, CompletedSetsAction listener) {
        this.context = context;
        this.exerciseObject = exerciseObject;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NestedActiveWorkoutAdapter.NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);

        View view = inflater.inflate((R.layout.active_workout_sets_layout_card), parent, false);

        db = new DatabaseSavedWorkouts(context);

        return new NestedActiveWorkoutAdapter.NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedActiveWorkoutAdapter.NestedViewHolder holder, int position) {
            ActiveWorkoutExerciseModel setModel = exerciseObject.get(position);

        // Set sets and reps
        holder.reps.setText(setModel.getReps());
        holder.weight.setText(setModel.getWeight());
        holder.setNum.setText(String.valueOf(position + 1));
        holder.checkBox.setChecked(setModel.isChecked());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setModel.setChecked(isChecked);
            if(isChecked) {
                onDataChange(setModel);
            }
        });
    }



    @Override
    public int getItemCount() {
        return exerciseObject.size();
    }

    public static class NestedViewHolder extends RecyclerView.ViewHolder {
        private TextView reps;
        private TextView weight;
        private TextView setNum;
        private CheckBox checkBox;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            reps = itemView.findViewById(R.id.set_row_reps);
            weight = itemView.findViewById(R.id.set_row_weight);
            setNum = itemView.findViewById(R.id.set_row_number);
            checkBox = itemView.findViewById(R.id.setCompleteCheckBox);

        }
    }

    private void onDataChange(ActiveWorkoutExerciseModel model){
        if(listener != null) {
            listener.onSetCompleted(model);
        }
    }
    public interface CompletedSetsAction {
        void onSetCompleted(ActiveWorkoutExerciseModel model);
        void onSetRemoved(ActiveWorkoutExerciseModel model);
    }
}
