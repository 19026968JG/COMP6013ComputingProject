package com.example.workouttrackerapplication.ui.active;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

        ArrayList<ActiveWorkoutExerciseNameModel> exercises = new ArrayList<>();
        ArrayList<DisplayExerciseObject> exercisesForAdapter = new ArrayList<>();

        exercises = db.getAllExercisesForWorkout(workoutId);

        for (int i = 0; i < exercises.size(); i++) {
            String tempName = exercises.get(i).getExerciseName();
            ArrayList<TupleRepsWeight> repsWeight = new ArrayList<>();

            for (int j = 0; j < exercises.get(i).getSets(); j++) {
                repsWeight.add(new TupleRepsWeight(exercises.get(i).getReps(),exercises.get(i).getWeight()));
            }
            exercisesForAdapter.add(new DisplayExerciseObject(tempName,repsWeight));
        }

        ActiveWorkoutAdapter adapter = new ActiveWorkoutAdapter(getContext(), exercisesForAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewExercise.setLayoutManager(layoutManager);
        recyclerViewExercise.setItemAnimator(new DefaultItemAnimator());
        recyclerViewExercise.setAdapter(adapter);

    }
}
