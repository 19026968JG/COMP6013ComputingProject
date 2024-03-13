package com.example.workouttrackerapplication;

public class ActiveWorkoutModel {
    private String exerciseName;
    private int sets;

    public ActiveWorkoutModel(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int sets() {
        return sets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

}
