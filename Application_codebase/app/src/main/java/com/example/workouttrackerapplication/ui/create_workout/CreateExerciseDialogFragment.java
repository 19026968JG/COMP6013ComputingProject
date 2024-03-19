package com.example.workouttrackerapplication.ui.create_workout;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.workouttrackerapplication.ExerciseModel;
import com.example.workouttrackerapplication.R;

public class CreateExerciseDialogFragment extends AppCompatDialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();



        builder.setView(inflater.inflate(R.layout.alert_dialogue, null))
                .setPositiveButton(R.string.add, (dialog, which) -> {

                    EditText exName = getDialog().findViewById(R.id.exercise_name_input);
                    EditText sets = getDialog().findViewById(R.id.sets_input);
                    EditText reps = getDialog().findViewById(R.id.reps_input);
                    EditText weight = getDialog().findViewById(R.id.weights_input);



                    ExerciseModel exerciseModel;

                    try{
                        exerciseModel = new ExerciseModel(
                                exName.getText().toString(),
                                Integer.parseInt(sets.getText().toString()),
                                Integer.parseInt(reps.getText().toString()),
                                Integer.parseInt(weight.getText().toString()));
                        CreateWorkoutFragment.displayList.add(exerciseModel);
                    }
                    catch (Exception e){
                        Toast.makeText(getContext(), "Error Adding Exercise " +
                                "\n Please Complete All Fields", Toast.LENGTH_LONG).show();

                    }
                    CreateWorkoutFragment.checkForUpdates();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> CreateExerciseDialogFragment.this.getDialog().cancel());

        return builder.create();
    }
}