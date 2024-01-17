package com.example.workouttrackerapplication;

public class WorkoutModel {

    private int id;
    private String exName;
    private int numOfSets;
    private double weight;
    private int numOfReps;

    //constructors
    public WorkoutModel(String exName, int numOfSets, double weight, int numOfReps) {
        this.exName = exName;
        this.numOfSets = numOfSets;
        this.weight = weight;
        this.numOfReps = numOfReps;
    }

    //toString


    @Override
    public String toString() {
        return "WorkoutModel{" +
                ", exName='" + exName + '\'' +
                ", numOfSets=" + numOfSets +
                ", weight=" + weight +
                ", numOfReps=" + numOfReps +
                '}';
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
