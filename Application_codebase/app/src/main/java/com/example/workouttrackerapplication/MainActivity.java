package com.example.workouttrackerapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;

import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_history, R.id.navigation_workouts)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);

         // TODO HIDE BOTTOM NAV BAR FOR CREATE WORKOUT AND ACTIVE WORKOUT FRAGMENTS

//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
//                int destinationId = navDestination.getId();
//                Log.d("dest changed log", "destination changed to " + destinationId);
//
//                // Check if the destination is one where you want to hide the bottom navigation
//                if (destinationId == R.id.active_workout_fragment || destinationId == R.id.create_workout_page) {
//                    Log.d("hide view", "view hidden");
//                    binding.navView.setVisibility(View.GONE);
//                } else {
//                    Log.d("show view", "view showing");
//                    binding.navView.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    public void bottomNavVisibility(int visibility) {
        binding.navView.setVisibility(visibility);
    }
}
