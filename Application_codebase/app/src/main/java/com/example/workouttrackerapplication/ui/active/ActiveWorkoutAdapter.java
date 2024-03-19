package com.example.workouttrackerapplication.ui.active;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;

import java.util.ArrayList;

public class ActiveWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    private Context context;
    private ArrayList<DisplayExerciseObject> activeWorkoutModels;

    public ActiveWorkoutAdapter(Context context, ArrayList<DisplayExerciseObject> activeWorkoutModels) {
        this.context = context;
        this.activeWorkoutModels = activeWorkoutModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case VIEW_TYPE_FOOTER:
                view = inflater.inflate(R.layout.active_workout_buttons, parent, false);
                return new FooterViewHolder(view);
            case VIEW_TYPE_NORMAL:
                view = inflater.inflate(R.layout.active_workout_exercise_layout_card, parent, false);
                return new MyViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            DisplayExerciseObject model = activeWorkoutModels.get(position);
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.exName.setText(model.getExName());

            ArrayList<String> reps = model.getAllReps();
            ArrayList<String> weight = model.getAllWeights();

            NestedActiveWorkoutAdapter nestedActiveWorkoutAdapter = new NestedActiveWorkoutAdapter(context, reps, weight);
            viewHolder.nestedRv.setLayoutManager(new LinearLayoutManager(context));
            viewHolder.nestedRv.setAdapter(nestedActiveWorkoutAdapter);
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            footerViewHolder.saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Save button clicked", Toast.LENGTH_SHORT).show();
                }
            });
            footerViewHolder.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Cancel button clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return activeWorkoutModels.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == activeWorkoutModels.size()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView exName;
        private RecyclerView nestedRv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exName = itemView.findViewById(R.id.exerciseRowHeader);
            nestedRv = itemView.findViewById(R.id.nestedRecyclerViewActicveWorkout);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private Button cancelButton, saveButton;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            cancelButton = itemView.findViewById(R.id.active_workout_cancel_button);
            saveButton = itemView.findViewById(R.id.active_workout_save_button);
        }
    }

    public void setData(ArrayList<DisplayExerciseObject> activeWorkoutModels) {
        this.activeWorkoutModels = activeWorkoutModels;
        notifyDataSetChanged();
    }
}
