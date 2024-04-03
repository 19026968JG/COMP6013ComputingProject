package com.example.workouttrackerapplication.ui.workouts;

import java.util.ArrayList;

public class WorkoutListModel {

    private String workoutName;
    private ArrayList<String> exercises;
    private ArrayList<String> sets;

    public WorkoutListModel(String workoutName, ArrayList<String> exercises, ArrayList<String> sets) {

        this.exercises = exercises;
        this.workoutName = workoutName;
        this.sets = sets;
    }

    public WorkoutListModel(){
    }

    public String toString() {
        StringBuilder returnList = new StringBuilder(workoutName.toUpperCase() + "\n");

        for(int i=0;i<exercises.size();i++) {
           returnList.append(exercises.get(i).toString());
           returnList.append(sets.get(i).toString());
        }

        return  returnList.toString();

    }

}
