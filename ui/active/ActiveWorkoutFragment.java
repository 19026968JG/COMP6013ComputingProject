package com.example.workouttrackerapplication.ui.active;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.ActiveWorkoutParentBinding;
import com.example.workouttrackerapplication.ui.workouts.WorkoutNameViewModel;
import com.example.workouttrackerapplication.ui.workouts.WorkoutsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class ActiveWorkoutFragment extends WorkoutsFragment {
    private
    ActiveWorkoutParentBinding binding;
    private RecyclerView recyclerViewExercise, recyclerViewSet;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseSavedWorkouts db;
    private int workoutId;
    private WorkoutNameViewModel workoutNameViewModel;
    private FragmentManager manager;
    private String workoutName;

    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        workoutNameViewModel = new ViewModelProvider(requireActivity()).get(WorkoutNameViewModel.class);
        db = new DatabaseSavedWorkouts(getContext());


        Bundle wOName = getArguments();
        if(wOName != null){
            workoutName = wOName.getString("workoutName");
        }

        workoutId = db.getWorkoutIdFromName(workoutName);

        binding = ActiveWorkoutParentBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        recyclerViewExercise = binding.recyclerView;
        recyclerViewSet = binding.recyclerView;

        addExercisesToView();


        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getContext(),"Use 'Cancel Workout' Button To Exit Without Saving", Toast.LENGTH_SHORT).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),onBackPressedCallback);



        return root;
    }


    private void addExercisesToView() {

        ArrayList<ActiveWorkoutExerciseModel> exercises;

        exercises = db.getAllExercisesForWorkout(workoutId);

        manager = getActivity().getSupportFragmentManager();
        ActiveWorkoutAdapter adapter = new ActiveWorkoutAdapter(getContext(), exercises, manager,workoutName);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewExercise.setLayoutManager(layoutManager);
        recyclerViewExercise.setItemAnimator(new DefaultItemAnimator());
        recyclerViewExercise.setAdapter(adapter);

    }

}
