package com.example.workouttrackerapplication.ui.create_workout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.WorkoutModel;
import com.example.workouttrackerapplication.databinding.AlertDialogueBinding;

import java.util.Objects;

public class createExerciseDialogFragment extends AppCompatDialogFragment {

    private AlertDialogueBinding binding;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final EditText exName = new EditText(getContext());
        final EditText sets = new EditText(getContext());

        builder.setView(inflater.inflate(R.layout.alert_dialogue, null))
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                            WorkoutModel workoutModel = new WorkoutModel(
                                    exName.getText().toString(),
                                    Integer.parseInt(sets.getText().toString()),
                                    0,
                                    0);
                            Toast.makeText(getContext(), workoutModel.toString(), Toast.LENGTH_SHORT).show();

                        /**catch(Exception e){
                            Toast.makeText(getContext(), "Fields Exercise Name and Sets, cannot be blank", Toast.LENGTH_LONG ).show();
                        }**/
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createExerciseDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
