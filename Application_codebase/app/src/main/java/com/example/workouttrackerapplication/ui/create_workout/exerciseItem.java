package com.example.workouttrackerapplication.ui.create_workout;

public class exerciseItem {
    int id;
    String exerciseName;
    int reps;
    int sets;
    double weight;

    public exerciseItem(int id, String exerciseName,int reps, int sets, double weight) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
    }

    public String getExerciseName() {
        return exerciseName;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
