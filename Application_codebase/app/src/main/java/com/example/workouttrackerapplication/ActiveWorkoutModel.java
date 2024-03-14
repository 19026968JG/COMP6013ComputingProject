package com.example.workouttrackerapplication;

public class ActiveWorkoutModel {
    private String exerciseName;
    private int sets;
    private float weight;
    private int reps;

    public ActiveWorkoutModel(String exerciseName, int sets, float weight, int reps) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.weight = weight;
        this.reps = reps;
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int sets() {
        return sets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

}
