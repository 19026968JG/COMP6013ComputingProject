package com.example.workouttrackerapplication.ui.create_workout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workouttrackerapplication.databinding.FragmentCreateWorkoutBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

class exercise {
    ArrayList<Object> exercise = new ArrayList<>();
      private void createExercise(){
          String exerciseTitle;
          int sets;
          int weight;
          int reps;
    }
}

public class createWorkoutFragment extends Fragment {


    FragmentCreateWorkoutBinding binding;
    public createWorkoutFragment() {
        // Required empty public constructor
    }
private BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateWorkoutBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        // Inflate the layout for this fragment






        return root;
    }
}