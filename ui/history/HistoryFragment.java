package com.example.workouttrackerapplication.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databinding.FragmentHistoryBinding;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutAdapter;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutExerciseModel;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private RecyclerView historyView, completedWorkouts;
    private DatabaseSavedWorkouts db;
    private RecyclerView.LayoutManager layoutManager;

    private FragmentManager manager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DatabaseSavedWorkouts(getContext());
        historyView =binding.displayHistory;

        addExercisesToView();

        return root;
    }

    private void addExercisesToView() {

        ArrayList<HistoryDisplayModel> exercises;

        exercises = db.getAllHistory();

        manager = getActivity().getSupportFragmentManager();
        HistoryDisplayAdapter adapter = new HistoryDisplayAdapter(getContext(),exercises);
        layoutManager = new LinearLayoutManager(getContext());
        historyView.setLayoutManager(layoutManager);
        historyView.setItemAnimator(new DefaultItemAnimator());
        historyView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}