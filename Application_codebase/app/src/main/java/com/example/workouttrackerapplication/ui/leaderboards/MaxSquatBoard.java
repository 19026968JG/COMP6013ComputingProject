package com.example.workouttrackerapplication.ui.leaderboards;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databinding.LeaderBoardSquatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MaxSquatBoard extends ChooseLeaderBoardFragment {

    private LeaderBoardSquatBinding binding;
    private ListView squatLeaderboardList;
    private ArrayList<String> allWeights;
    private DatabaseSavedWorkouts db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        binding = LeaderBoardSquatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DatabaseSavedWorkouts(getContext());
        allWeights = new ArrayList<>();
        squatLeaderboardList = binding.squatLeaderboardList;

        DatabaseReference fireDbRef = FirebaseDatabase
                .getInstance("https://workoutdatabaseserver-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("LeaderboardValues/Users");

         fireDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 int position = 1;

                 for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                     allWeights.add(" Rank: " + position
                             + "\t\t\t  " + dataSnapshot.child("userName").getValue(String.class)
                             + " \t\t\t Highest: "
                             + dataSnapshot.child("SQUAT").getValue(Long.class).toString());

                     position++;
                 }
                 populateLeaderboard();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Log.e("DataBase Error", error.getMessage());
             }
         });

        return root;
    }

    private void populateLeaderboard() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.leaderboard_item_custom_layout,allWeights);
       squatLeaderboardList.setAdapter(adapter);
    }

}
