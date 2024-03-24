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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class ActiveWorkoutFragment extends Fragment {
    private
    ActiveWorkoutParentBinding binding;
    private RecyclerView recyclerViewExercise;
    private RecyclerView recyclerViewSet;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseSavedWorkouts db;
    private int workoutId;
    private WorkoutNameViewModel workoutNameViewModel;
    private FragmentManager manager;

    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        workoutNameViewModel = new ViewModelProvider(requireActivity()).get(WorkoutNameViewModel.class);
        db = new DatabaseSavedWorkouts(getContext());
        workoutId = db.getWorkoutIdFromName(workoutNameViewModel.getWorkoutName());

        binding = ActiveWorkoutParentBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);

        recyclerViewExercise = binding.recyclerView;
        recyclerViewSet = binding.recyclerView;

        addExercisesToView();

        OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setEnabled(false);
                Toast.makeText(getContext(), "Exiting", Toast.LENGTH_SHORT).show();

            }
        };
        return root;
    }


    private void addExercisesToView() {

        ArrayList<ActiveWorkoutExerciseModel> exercises = new ArrayList<>();

        exercises = db.getAllExercisesForWorkout(workoutId);

        manager = getActivity().getSupportFragmentManager();
        ActiveWorkoutAdapter adapter = new ActiveWorkoutAdapter(getContext(), exercises,manager);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewExercise.setLayoutManager(layoutManager);
        recyclerViewExercise.setItemAnimator(new DefaultItemAnimator());
        recyclerViewExercise.setAdapter(adapter);

    }
}
