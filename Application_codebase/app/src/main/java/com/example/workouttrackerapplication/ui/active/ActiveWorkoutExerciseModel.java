package com.example.workouttrackerapplication.ui.active;

import androidx.annotation.NonNull;

public class ActiveWorkoutExerciseModel {
    private String exerciseName;
    private int sets;
    private String weight;
    private String reps;
    private boolean isChecked;

    public ActiveWorkoutExerciseModel(String exerciseName, int sets, String weight, String reps, boolean isChecked) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.weight = weight;
        this.reps = reps;
        this.isChecked = isChecked;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @NonNull
    @Override
    public String toString() {
        return "exercise: " + exerciseName
                + " sets: " + sets
                + " weight: " + weight
                + " reps: " + reps
                + " isChecked: " + isChecked;

    }
}
