package com.example.workouttrackerapplication.ui.leaderboards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.LeaderboardNavigationScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChooseLeaderBoardFragment extends Fragment {

    private Button squatButton, benchButton, deadliftButton, totalButton;
    private LeaderboardNavigationScreenBinding binding;

    public ChooseLeaderBoardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LeaderboardNavigationScreenBinding.inflate(inflater, container, false);
        binding.leaderboaradButtonCard.setVisibility(View.VISIBLE);
        binding.leaderboardsSelectTitle.setVisibility(View.VISIBLE);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);

        FragmentManager manger = getChildFragmentManager();
        FragmentTransaction transaction = manger.beginTransaction();

        squatButton  = binding.squatLeaderboardButton;
        benchButton = binding.benchLeaderboardButton;
        deadliftButton = binding.deadliftLeaderboardButton;
        totalButton = binding.totalLeaderboardButton;

        squatButton.setOnClickListener(v -> {
            transaction.replace(R.id.navigation_leaderboards, new MaxSquatBoard());
            transaction.addToBackStack("leaderboard");
            binding.leaderboaradButtonCard.setVisibility(View.GONE);
            binding.leaderboardsSelectTitle.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.GONE);
            transaction.commit();
        });

        benchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.navigation_leaderboards, new MaxBenchBoard());
                transaction.addToBackStack("leaderboard");
                binding.leaderboaradButtonCard.setVisibility(View.GONE);
                binding.leaderboardsSelectTitle.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
                transaction.commit();
            }
        });

        deadliftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.navigation_leaderboards, new MaxDeadliftBoard());
                transaction.addToBackStack("leaderboard");
                binding.leaderboaradButtonCard.setVisibility(View.GONE);
                binding.leaderboardsSelectTitle.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
                transaction.commit();
            }
        });

        totalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.navigation_leaderboards, new MaxTotalBoard());
                transaction.addToBackStack("leaderboard");
                binding.leaderboaradButtonCard.setVisibility(View.GONE);
                binding.leaderboardsSelectTitle.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
                transaction.commit();
            }
        });

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };


        return binding.getRoot();
    }

}
