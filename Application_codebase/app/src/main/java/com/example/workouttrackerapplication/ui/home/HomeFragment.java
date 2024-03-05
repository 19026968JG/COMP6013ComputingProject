package com.example.workouttrackerapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.FragmentHomeBinding;
import com.example.workouttrackerapplication.ui.create_workout.createWorkoutFragment;
import com.example.workouttrackerapplication.ui.user.UserLoginFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.greetingTextHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        try {
            DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());

            // check if there is a user already and load login if not
            if (db.checkDatabaseExists(requireContext()) && db.checkEmptyUsersTable()) {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.container,new UserLoginFragment());
                transaction.addToBackStack("add username transition slide");
                transaction.commit();
            }

        }
        catch (Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}