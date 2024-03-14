package com.example.workouttrackerapplication.ui.workouts;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.MainActivity;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.FragmentWorkoutsBinding;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutFragment;
import com.example.workouttrackerapplication.ui.create_workout.createWorkoutFragment;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment {

    private ListView savedWorkoutsList;
    private FragmentWorkoutsBinding binding;
    private ArrayList<String> workoutsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        savedWorkoutsList = binding.savedWorkoutsList;
        DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());
        workoutsList = new ArrayList<>();
        workoutsList = db.getAllWorkoutNames();

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

        binding.savedWorkoutsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(requireContext())

                    .setTitle("Start " + workoutsList.get(position) + "?")
                    .setPositiveButton("Yes", (dialog,which) -> {

                        FragmentManager manager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        Bundle wId = new Bundle();
                        wId.putInt("database_id", db.getWorkoutId());
                        manager.setFragmentResult("pass_database_id", wId);

                        transaction.replace(R.id.workouts_page, new ActiveWorkoutFragment());
                        transaction.addToBackStack("Active Workout Begin Transaction");
                        binding.fabAddWorkoutButton.hide();
                        transaction.commit();
                    })
                        .setNegativeButton("No", (dialog,which)-> {
                        dialog.dismiss();
                    })
                        .create()
                        .show();
            }
        });

        return root;
    }

    private void updateListDisplay() {
        ArrayAdapter<String> workoutListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, workoutsList);
        savedWorkoutsList.setAdapter(workoutListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}