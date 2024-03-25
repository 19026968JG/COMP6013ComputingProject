package com.example.workouttrackerapplication.ui.history;

import com.example.workouttrackerapplication.ui.active.ActiveWorkoutExerciseModel;

import java.util.ArrayList;

public class HistoryDisplayModel {

    private String workoutName;
    private ArrayList<ActiveWorkoutExerciseModel> exerciseList;


    public HistoryDisplayModel(String workoutName, ArrayList<ActiveWorkoutExerciseModel> exerciseList){
        this.workoutName = workoutName;
        this.exerciseList = exerciseList;
    }
}
