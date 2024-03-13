package com.example.workouttrackerapplication.ui.active;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.workouttrackerapplication.ActiveWorkoutModel;
import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databinding.ActiveWorkoutBinding;
import com.example.workouttrackerapplication.SetModel;
import java.util.ArrayList;


public class ActiveWorkoutFragment extends Fragment {

    private ActiveWorkoutBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<ActiveWorkoutModel> exerciseList;
    private ArrayList<SetModel> setsList;
    private DatabaseSavedWorkouts db;



    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        binding = ActiveWorkoutBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

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
        String[] exerciseNames = db.getExerciseNames();


        for (int i = 0; i < exerciseNames.length; i++) {
            exerciseList.add(new ActiveWorkoutModel(exerciseNames[i]));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
