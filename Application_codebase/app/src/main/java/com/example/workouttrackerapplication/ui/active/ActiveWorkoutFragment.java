package com.example.workouttrackerapplication.ui.active;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.workouttrackerapplication.ActiveWorkoutModel;
import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.MainActivity;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.ActiveWorkoutBinding;
import com.example.workouttrackerapplication.SetModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class ActiveWorkoutFragment extends Fragment {

    private ActiveWorkoutBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<ActiveWorkoutModel> exerciseList;
    private ArrayList<Integer> setsList;
    private Map<Float,Integer> weightRepsList;
    private DatabaseSavedWorkouts db;
    private int workoutId;

    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        getParentFragmentManager().setFragmentResultListener("pass_database_id", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                workoutId = bundle.getInt("database_id");
            }
        });

        binding = ActiveWorkoutBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        db = new DatabaseSavedWorkouts(getContext());

        recyclerView = binding.recyclerView;
        exerciseList = new ArrayList<>();
        setsList = new ArrayList<>();

        populateWorkoutView();

        ActiveWorkoutAdapter adapter = new ActiveWorkoutAdapter(getContext(), exerciseList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return root;
    }

    private void populateWorkoutView() {
        String[] exerciseNames = db.getExerciseNames(workoutId);
        // get sets add to map for Map<names,sets>

        //get weight reps add to map for Map<weight,reps>


        for (int i = 0; i < exerciseNames.length; i++) {
            exerciseList.add(new ActiveWorkoutModel(exerciseNames[i]));
        }
    }

}
