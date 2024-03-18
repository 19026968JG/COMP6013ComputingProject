package com.example.workouttrackerapplication.ui.active;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.ui.workouts.WorkoutNameViewModel;
import com.example.workouttrackerapplication.databinding.ActiveWorkoutBinding;

import java.util.ArrayList;


public class ActiveWorkoutFragment extends Fragment {

    private ActiveWorkoutBinding binding;
    private RecyclerView recyclerViewExercise;
    private RecyclerView recyclerViewSet;
    private RecyclerView.LayoutManager layoutManager;
    private ConstraintLayout nestedSetsLayout;
    private ArrayList<ActiveWorkoutExerciseNameModel> exerciseList;
    private DatabaseSavedWorkouts db;
    private int workoutId;
    private WorkoutNameViewModel workoutNameViewModel;

    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        workoutNameViewModel = new ViewModelProvider(requireActivity()).get(WorkoutNameViewModel.class);
        db = new DatabaseSavedWorkouts(getContext());
        workoutId = db.getWorkoutIdFromName(workoutNameViewModel.getWorkoutName());

        binding = ActiveWorkoutBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        recyclerViewExercise = binding.recyclerView;
        recyclerViewSet = binding.recyclerView;
        exerciseList = new ArrayList<>();

        ArrayList<ActiveWorkoutExerciseNameModel> testListForSets = new ArrayList<>();
        testListForSets.add(new ActiveWorkoutExerciseNameModel("fart", 5,"100","15"));

        addExercisesToView();

        return root;
    }

    private void addExercisesToView() {
        ActiveWorkoutAdapter adapter = new ActiveWorkoutAdapter(getContext(), db.getAllExercisesForWorkout(workoutId));
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewExercise.setLayoutManager(layoutManager);
        recyclerViewExercise.setItemAnimator(new DefaultItemAnimator());
        recyclerViewExercise.setAdapter(adapter);

//        NestedActiveWorkoutAdapter nestedActiveWorkoutAdapter = new NestedActiveWorkoutAdapter(getContext(), db.getAllExercisesForWorkout(workoutId));
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerViewSet.setLayoutManager(layoutManager);
//        recyclerViewSet.setAdapter(nestedActiveWorkoutAdapter);

    }

    private void addSetsToView() {
        ActiveWorkoutAdapter adapter = new ActiveWorkoutAdapter(getContext(), db.getAllExercisesForWorkout(workoutId));
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewExercise.setLayoutManager(layoutManager);
        recyclerViewExercise.setItemAnimator(new DefaultItemAnimator());
        recyclerViewExercise.setAdapter(adapter);
    }
}
