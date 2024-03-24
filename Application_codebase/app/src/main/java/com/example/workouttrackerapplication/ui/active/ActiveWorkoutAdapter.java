package com.example.workouttrackerapplication.ui.active;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.ui.workouts.WorkoutsFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class ActiveWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnCheckboxCountChangeListener {
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private Context context;
    private ArrayList<ActiveWorkoutExerciseModel> activeWorkoutModels;
    private ArrayList<ActiveWorkoutExerciseModel> completedSets = new ArrayList<>() ;
    private ArrayList<ActiveWorkoutExerciseModel> uncheckedSets = new ArrayList<>() ;
    private DatabaseSavedWorkouts db;
    private FragmentManager manager;

    public ActiveWorkoutAdapter(Context context, ArrayList<ActiveWorkoutExerciseModel> activeWorkoutModels, FragmentManager manager) {
        this.context = context;
        this.activeWorkoutModels = activeWorkoutModels;
        this.manager = manager;
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
            ActiveWorkoutExerciseModel model = activeWorkoutModels.get(position);
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.exName.setText(model.getExerciseName());

            ArrayList<ActiveWorkoutExerciseModel> nestedModels = new ArrayList<>();
            for (int i = 0; i < model.getSets(); i++) {
                nestedModels.add(activeWorkoutModels.get(position));
            }

            NestedActiveWorkoutAdapter nestedActiveWorkoutAdapter = new NestedActiveWorkoutAdapter(context, nestedModels, this);
            viewHolder.nestedRv.setLayoutManager(new LinearLayoutManager(context));
            viewHolder.nestedRv.setAdapter(nestedActiveWorkoutAdapter);
        }
        else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            footerViewHolder.saveButton.setOnClickListener(v -> {
                Toast.makeText(context, "Save button clicked", Toast.LENGTH_SHORT).show();


                //TODO build a Dialog here to confirm save
                //Check if any of the values are higher for squat bench and deadlift

                uncheckedSets.removeAll(completedSets);


                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.active_workout_fragment, new WorkoutsFragment());
                transaction.addToBackStack(null);
                transaction.commit();

            });

            footerViewHolder.cancelButton.setOnClickListener(v ->
                    Toast.makeText(context, "Cancel button clicked", Toast.LENGTH_SHORT).show());

                // TODO build a Dialog here to confirm cancel
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

    @Override
    public void onCheckboxChecked(ActiveWorkoutExerciseModel completed) {
         completedSets.add(completed);
         System.out.println(completedSets);
    }

    @Override
    public void onCheckBoxUnchecked(ActiveWorkoutExerciseModel unchecked) {
        uncheckedSets.add(unchecked);
        System.out.println(unchecked);
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

}
