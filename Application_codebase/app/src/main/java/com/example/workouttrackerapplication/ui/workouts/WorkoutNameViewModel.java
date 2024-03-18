package com.example.workouttrackerapplication.ui.workouts;

import androidx.lifecycle.ViewModel;

public class WorkoutNameViewModel extends ViewModel {

    // exists only to pass across the workout name to the active workout fragment

    private String workoutName;

    public String getWorkoutName() {
        return workoutName;
    }

    public void updateWorkoutName(String name) {
        workoutName = name;
    }
}
