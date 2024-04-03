package com.example.workouttrackerapplication;

public class SetModel {

    private int sets;

    private int reps;

    public SetModel(int sets, int reps) {
        this.sets = sets;
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }
}
