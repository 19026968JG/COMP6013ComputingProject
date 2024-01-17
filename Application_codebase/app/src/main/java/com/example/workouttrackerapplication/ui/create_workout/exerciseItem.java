package com.example.workouttrackerapplication.ui.create_workout;

public class exerciseItem {

    String exerciseName;
    int sets;

    double weight;

    public exerciseItem(String exerciseName, int sets, double weight) {
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.weight = weight;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
