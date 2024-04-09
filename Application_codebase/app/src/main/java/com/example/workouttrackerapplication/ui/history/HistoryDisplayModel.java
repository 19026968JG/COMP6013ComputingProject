package com.example.workouttrackerapplication.ui.history;

import com.example.workouttrackerapplication.ui.active.ActiveWorkoutExerciseModel;

import java.util.ArrayList;

public class HistoryDisplayModel {

    private String workoutName;
    private String date;

    public HistoryDisplayModel(String workoutName, String date){
        this.workoutName = workoutName;
        this.date = date;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getDate() {
        return date;
    }

}
