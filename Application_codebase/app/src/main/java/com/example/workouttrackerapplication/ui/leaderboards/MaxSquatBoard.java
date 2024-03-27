package com.example.workouttrackerapplication.ui.leaderboards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databinding.FragmentWorkoutsBinding;
import com.example.workouttrackerapplication.databinding.LeaderBoardSquatBinding;
import com.example.workouttrackerapplication.databinding.LeaderboardNavigationScreenBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MaxSquatBoard extends Fragment {

    private LeaderBoardSquatBinding binding;

    private ArrayList<DataSnapshot> allMaxWeights;
    private DatabaseSavedWorkouts db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        binding = LeaderBoardSquatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DatabaseSavedWorkouts(getContext());
        allMaxWeights = new ArrayList<>();
        Map<String,Double> displayMap = new HashMap<>();



        DatabaseReference fireDbRef = FirebaseDatabase
                .getInstance("https://workoutdatabaseserver-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("LeaderboardValues/Users");

         fireDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot dataSnapshot :
                         snapshot.getChildren()) {
                     allMaxWeights.add(snapshot);
//                     displayMap.put(snapshot.getChildren().toString(), snapshot.);
                 }
                 System.out.println(allMaxWeights);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        return root;

    }

}
