package com.example.workouttrackerapplication.ui.create_workout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.workouttrackerapplication.ExerciseModel;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.AlertDialogueBinding;

public class CreateExerciseDialogFragment extends AppCompatDialogFragment {

    private AlertDialogueBinding binding;
    public ArrayAdapter createWorkoutArrayAdapter;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();



        builder.setView(inflater.inflate(R.layout.alert_dialogue, null))
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText exName = (EditText)getDialog().findViewById(R.id.exercise_name_input);
                        EditText sets = (EditText)getDialog().findViewById(R.id.sets_input);
                        EditText reps = (EditText)getDialog().findViewById(R.id.reps_input);
                        EditText weight = (EditText)getDialog().findViewById(R.id.weights_input);



                        ExerciseModel exerciseModel;

                        try{
                            exerciseModel = new ExerciseModel(-1,
                                    exName.getText().toString(),
                                    Integer.parseInt(sets.getText().toString()),
                                    Integer.parseInt(reps.getText().toString()),
                                    Integer.parseInt(weight.getText().toString()));
                        }
                        catch (Exception e){
                            Toast.makeText(getContext(), "Error Adding Exercise", Toast.LENGTH_SHORT).show();
                            exerciseModel = new ExerciseModel(-1, "Error",-1,-1,-1);
                        }

                        createWorkoutFragment.displayList.add(exerciseModel);
                        updateListView();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreateExerciseDialogFragment.this.getDialog().cancel();
                    }

                });

        return builder.create();
    }

    public void updateListView() {
        createWorkoutArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,createWorkoutFragment.displayList);
        createWorkoutFragment.wList.setAdapter(createWorkoutArrayAdapter);
    }
}