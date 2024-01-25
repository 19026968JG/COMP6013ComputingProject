package com.example.workouttrackerapplication;

import java.util.List;

public class WorkoutListModel {

    private int id;
    private String workoutName;
    private List<ExerciseModel> exerciseModel;

    public WorkoutListModel(int id, String workoutName, List<ExerciseModel> exerciseModel) {
        this.id = id;
        this.exerciseModel = exerciseModel;
        this.workoutName =workoutName;
    }

    public WorkoutListModel(){

    }

    public String toString() {
        return  workoutName.toUpperCase() + '\n' +
                exerciseModel;
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

    public List<ExerciseModel> getExerciseModel() {
        return exerciseModel;
    }

    public void setExerciseModel(List<ExerciseModel> exerciseModel) {
        this.exerciseModel = exerciseModel;
    }
}
