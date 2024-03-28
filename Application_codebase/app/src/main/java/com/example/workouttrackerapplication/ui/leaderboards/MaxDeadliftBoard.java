package com.example.workouttrackerapplication.ui.leaderboards;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databinding.LeaderBoardDeadliftBinding;
import com.example.workouttrackerapplication.databinding.LeaderBoardSquatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxDeadliftBoard extends ChooseLeaderBoardFragment{

    private LeaderBoardDeadliftBinding binding;
    private ListView deadliftLeaderboardList;
    private ArrayList<String> allWeights;
    private DatabaseSavedWorkouts db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState){

        binding = LeaderBoardDeadliftBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DatabaseSavedWorkouts(getContext());
        allWeights = new ArrayList<>();
        deadliftLeaderboardList = binding.deadliftLeaderboardList;

        Map<String, Long> sortWeights = new HashMap<>();

        DatabaseReference fireDbRef = FirebaseDatabase
                .getInstance("https://workoutdatabaseserver-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("LeaderboardValues/Users");
        fireDbRef.orderByChild("DEADLIFT");
        fireDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int position = 1;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String key = dataSnapshot.child("userName").getValue(String.class);
                    Long value = dataSnapshot.child("DEADLIFT").getValue(Long.class);

                    if(value != -1) {
                        sortWeights.put(key,value);
                    }
                }
                List<Map.Entry<String,Long>> sortedEntries = new ArrayList<>(sortWeights.entrySet());
                sortedEntries.sort(Collections.reverseOrder(Map.Entry.comparingByValue()));

                for(Map.Entry<String,Long> entry : sortedEntries){
                    String key = entry.getKey();
                    String value = entry.getValue().toString();

                    allWeights.add(" Rank: " + position
                            + "\t\t\t  " + key
                            + " \t\t\t Highest: "
                            + value);

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
                parentFragmentManager.popBackStack();
                fragmentTransaction.commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),onBackPressedCallback);
        return root;
    }

    private void populateLeaderboard() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.leaderboard_item_custom_layout,allWeights);
        deadliftLeaderboardList.setAdapter(adapter);
    }
}
