package com.example.workouttrackerapplication.ui.workouts;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.activity.OnBackPressedCallback;

import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.FragmentWorkoutsBinding;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutFragment;
import com.example.workouttrackerapplication.ui.create_workout.CreateWorkoutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment {

    private ListView savedWorkoutsList;
    private FragmentWorkoutsBinding binding;
    private ArrayList<String> workoutsList;
    private WorkoutNameViewModel workoutNameViewModel;
    protected String workoutName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        savedWorkoutsList = binding.savedWorkoutsList;
        DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());
        workoutsList = new ArrayList<>();
        workoutsList = db.getAllWorkoutNames();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);
        updateListDisplay();

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getContext(),"Use 'Cancel Workout' Button To Exit Without Saving", Toast.LENGTH_SHORT).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),onBackPressedCallback);

        // Floating Action Button Functionality
        binding.fabAddWorkoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

               transaction.add(R.id.workouts_page,  CreateWorkoutFragment.class,null);
                binding.cardView2.setVisibility(View.GONE);
                binding.fabAddWorkoutButton.hide();
                transaction.commit();


            }
        });

        binding.savedWorkoutsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object clickedItem = parent.getItemAtPosition(position);

                new AlertDialog.Builder(requireContext())

                    .setTitle("Start " + workoutsList.get(position) + "?")
                    .setPositiveButton("Yes", (dialog,which) -> {

                        FragmentManager manager = getChildFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();

                        workoutNameViewModel = new ViewModelProvider(requireActivity()).get(WorkoutNameViewModel.class);
                        workoutNameViewModel.updateWorkoutName(clickedItem.toString());
                        workoutName = workoutNameViewModel.getWorkoutName();

                        Bundle wOName = new Bundle();
                        wOName.putString("workoutName", workoutName);
                        ActiveWorkoutFragment activeWorkoutFragment = new ActiveWorkoutFragment();
                        activeWorkoutFragment.setArguments(wOName);

                        transaction.replace(R.id.workouts_page, activeWorkoutFragment);
                        transaction.addToBackStack("Active Workout Begin Transaction");
                        binding.fabAddWorkoutButton.hide();
                        binding.cardView2.setVisibility(View.GONE);
                        navBar.setVisibility(View.GONE);
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