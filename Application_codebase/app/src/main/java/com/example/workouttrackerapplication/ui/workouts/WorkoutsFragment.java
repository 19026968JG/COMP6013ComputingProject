package com.example.workouttrackerapplication.ui.workouts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workouttrackerapplication.MainActivity;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.FragmentWorkoutsBinding;
import com.example.workouttrackerapplication.ui.create_workout.createWorkoutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WorkoutsFragment extends Fragment {
    private FragmentWorkoutsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WorkoutsViewModel workoutsViewModel =
                new ViewModelProvider(this).get(WorkoutsViewModel.class);

        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.addNewWorkoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.workouts_page,new createWorkoutFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}