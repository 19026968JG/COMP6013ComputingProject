package com.example.workouttrackerapplication.ui.active;

public class TupleRepsWeight {

    private String reps;
    private String weight;

    public TupleRepsWeight(String reps, String weight){
        this.weight=weight;
        this.reps=reps;
    }

    public String getReps() {
        return reps;
    }

    public String getWeight() {
        return weight;
    }
}
