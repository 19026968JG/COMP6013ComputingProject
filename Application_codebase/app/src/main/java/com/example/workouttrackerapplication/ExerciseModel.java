package com.example.workouttrackerapplication;

public class ExerciseModel {

    private String exName;
    private int numOfSets;
    private double weight;
    private int numOfReps;

    //constructors
    public ExerciseModel(String exName, int numOfSets, int numOfReps, double weight) {

        this.exName = exName;
        this.numOfSets = numOfSets;
        this.weight = weight;
        this.numOfReps = numOfReps;
    }
    public ExerciseModel(){

    }
    @Override
    public String toString() {
        return  exName.toUpperCase() + '\n' +
                "Sets=" + numOfSets  + " | Reps=" + numOfReps + " | Weight=" + weight + "kg";
    }
    //getters and setters
    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public int getNumOfSets() {
        return numOfSets;
    }

    public void setNumOfSets(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getNumOfReps() {
        return numOfReps;
    }

    public void setNumOfReps(int numOfReps) {
        this.numOfReps = numOfReps;
    }

}
