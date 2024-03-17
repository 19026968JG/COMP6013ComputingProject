package com.example.workouttrackerapplication;

import androidx.lifecycle.ViewModel;

public class WorkoutNameViewModel extends ViewModel {

    private String workoutName;

    public String getWorkoutName() {
        return workoutName;
    }

    public void updateWorkoutName(String name) {
        workoutName = name;
    }
}
