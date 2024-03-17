package com.example.workouttrackerapplication.ui.active;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.workouttrackerapplication.ActiveWorkoutExerciseNameModel;
import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.WorkoutNameViewModel;
import com.example.workouttrackerapplication.databinding.ActiveWorkoutBinding;

import java.util.ArrayList;
import java.util.Map;


public class ActiveWorkoutFragment extends Fragment {

    private ActiveWorkoutBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<ActiveWorkoutExerciseNameModel> exerciseList;
    private ArrayList<Integer> setsList;
    private Map<Float,Integer> weightRepsList;
    private DatabaseSavedWorkouts db;
    private RecyclerView.LayoutManager layoutManager;
    private int workoutId;
    private WorkoutNameViewModel workoutNameViewModel;
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        workoutNameViewModel = new ViewModelProvider(requireActivity()).get(WorkoutNameViewModel.class);
        db = new DatabaseSavedWorkouts(getContext());
        workoutId = db.getWorkoutIdFromName(workoutNameViewModel.getWorkoutName());

        binding = ActiveWorkoutBinding.inflate(inflater, container,false);
        View root = binding.getRoot();



        recyclerView = binding.recyclerView;
        exerciseList = new ArrayList<>();
        setsList = new ArrayList<>();

        populateWorkoutView();
        setAdapter();

        return root;
    }

    private void setAdapter() {
        ActiveWorkoutAdapter adapter = new ActiveWorkoutAdapter(getContext(), db.getAllExerciseNamesForWorkout(workoutId));
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void populateWorkoutView() {
        //TODO
        // maybe delete method
    }

}
