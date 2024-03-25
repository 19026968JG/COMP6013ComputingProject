package com.example.workouttrackerapplication.ui.leaderboards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.workouttrackerapplication.databinding.LeaderboardNavigationScreenBinding;

public class ChooseLeaderBoardFragment extends Fragment {

    private Button squatButton;
    private Button benchButton;
    private Button deadliftButton;
    private Button totalButton;
    private LeaderboardNavigationScreenBinding binding;


    public ChooseLeaderBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LeaderboardNavigationScreenBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}
