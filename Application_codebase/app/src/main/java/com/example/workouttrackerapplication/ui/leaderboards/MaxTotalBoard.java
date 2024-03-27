package com.example.workouttrackerapplication.ui.leaderboards;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databinding.LeaderBoardSquatBinding;
import com.example.workouttrackerapplication.databinding.LeaderBoardTotalBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MaxTotalBoard extends ChooseLeaderBoardFragment{

    private LeaderBoardTotalBinding binding;
    private ListView totalLeaderboardList;
    private ArrayList<String> allWeights;
    private DatabaseSavedWorkouts db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        binding = LeaderBoardTotalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DatabaseSavedWorkouts(getContext());
        allWeights = new ArrayList<>();
        totalLeaderboardList = binding.totalLeaderboardList;

        DatabaseReference fireDbRef = FirebaseDatabase
                .getInstance("https://workoutdatabaseserver-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("LeaderboardValues/Users");

        fireDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int position = 1;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    double total = 0;

                    total = (dataSnapshot.child("SQUAT").getValue(Long.class)
                            + dataSnapshot.child("BENCH").getValue(Long.class)
                            + dataSnapshot.child("DEADLIFT").getValue(Long.class));


                    allWeights.add(" Rank: " + position
                            + "\t\t\t  " + dataSnapshot.child("userName").getValue(String.class)
                            + " \t\t\t Highest: "
                            + Double.toString(total));

                    position++;
                }
                populateLeaderboard();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DataBase Error", error.getMessage());
            }
        });


        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                FragmentManager parentFragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = parentFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.navigation_leaderboards, new ChooseLeaderBoardFragment());
                fragmentTransaction.commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),onBackPressedCallback);
        return root;
    }

    private void populateLeaderboard() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.leaderboard_item_custom_layout,allWeights);
        totalLeaderboardList.setAdapter(adapter);
    }
}
