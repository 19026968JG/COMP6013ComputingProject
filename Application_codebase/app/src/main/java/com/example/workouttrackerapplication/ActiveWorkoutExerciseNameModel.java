package com.example.workouttrackerapplication;

public class ActiveWorkoutExerciseNameModel {
    private String exerciseName;
    private int sets;
    private String weight;
    private String reps;

    public ActiveWorkoutExerciseNameModel(String exerciseName, int sets, String weight, String reps) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.weight = weight;
        this.reps = reps;
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
}
