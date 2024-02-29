package com.example.workouttrackerapplication.ui.workouts;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.FragmentWorkoutsBinding;
import com.example.workouttrackerapplication.ui.create_workout.createWorkoutFragment;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment {

    public ListView savedWorkoutsList;
    private FragmentWorkoutsBinding binding;
    private ArrayList<String> workoutsList;
    private DatabaseSavedWorkouts db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        savedWorkoutsList = binding.savedWorkoutsList;
        db = new DatabaseSavedWorkouts(getContext());
        workoutsList = new ArrayList<>();

        updateListDisplay();

        // Floating Action Button Functionality
        binding.fabAddWorkoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(R.id.workouts_page, new createWorkoutFragment());
                transaction.addToBackStack("Create workout transition");
                binding.fabAddWorkoutButton.hide();
                transaction.commit();

            }
        });

        return root;
    }
    private ArrayList<String> getAvailableWorkouts() {
        return null;
    }
    private void updateListDisplay() {
        ArrayList<String> subDisplayList = new ArrayList<>();

            /* *
            *
            * TODO BUILD THE WORKOUT LIST MODEL FROM THE DATABASE THEN OUTPUT TO THE LIST
            * get name then for the length of the row? maybe don't need to display sets
            *
            * */

            subDisplayList.add(db.getWorkoutName());

        ArrayAdapter<String> workoutListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, subDisplayList);
        savedWorkoutsList.setAdapter(workoutListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}