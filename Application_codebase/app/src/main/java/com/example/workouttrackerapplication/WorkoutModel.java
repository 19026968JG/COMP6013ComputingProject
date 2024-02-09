package com.example.workouttrackerapplication;

public class WorkoutModel {

    private int id;
    private String workoutName;


    public WorkoutModel(int id, String workoutName){
        this.id = id;
        this.workoutName = workoutName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}
