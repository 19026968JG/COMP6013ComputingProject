package com.example.workouttrackerapplication;

public class WorkoutModel {

    // private int id;
    private String workoutName;


    public WorkoutModel( String workoutName){
        this.workoutName = workoutName;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}
