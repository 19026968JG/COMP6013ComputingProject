package com.example.workouttrackerapplication.ui.active;

import java.util.ArrayList;

public class DisplayExerciseObject {

    private String exName;
    private ArrayList<TupleRepsWeight> repsWeights;

    public DisplayExerciseObject (String exName, ArrayList<TupleRepsWeight> repsWeights) {
        this.exName=exName;
        this.repsWeights=repsWeights;
    }

    public String getExName() {
        return exName;
    }

    public ArrayList<TupleRepsWeight> getRepsWeights() {
        return repsWeights;
    }

    public ArrayList<String> getAllReps() {
        ArrayList<String> reps = new ArrayList<>();
        ArrayList<TupleRepsWeight> repsWeightArrayList = getRepsWeights();

        for (int i = 0; i < repsWeightArrayList.size(); i++) {
            reps.add(repsWeightArrayList.get(i).getReps());
        }
        return reps;
    }

    public ArrayList<String> getAllWeights() {
        ArrayList<String> weights = new ArrayList<>();
        ArrayList<TupleRepsWeight> repsWeightArrayList = getRepsWeights();

        for (int i = 0; i < repsWeightArrayList.size(); i++) {
            weights.add(repsWeightArrayList.get(i).getWeight());
        }
        return weights;
    }
}
