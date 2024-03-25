package com.example.workouttrackerapplication.ui.active;

import java.util.ArrayList;

public interface OnCheckboxCountChangeListener {

    void addCompletedSetToList(ActiveWorkoutExerciseModel completed);
    void removeFromCompletedSetList(ActiveWorkoutExerciseModel completed);

    ArrayList<ActiveWorkoutExerciseModel> getCompletedSets();

}
