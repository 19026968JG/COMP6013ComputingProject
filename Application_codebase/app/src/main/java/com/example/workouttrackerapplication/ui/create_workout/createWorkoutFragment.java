package com.example.workouttrackerapplication.ui.create_workout;
import com.example.workouttrackerapplication.DatabaseCreateWorkout;
import com.example.workouttrackerapplication.DatabaseWorkoutList;
import com.example.workouttrackerapplication.ExerciseModel;
import com.example.workouttrackerapplication.databinding.AlertDialogueBinding;
import com.example.workouttrackerapplication.databinding.FragmentCreateWorkoutBinding;
import com.example.workouttrackerapplication.R;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class createWorkoutFragment extends Fragment {

    FragmentCreateWorkoutBinding binding;
    DatabaseWorkoutList databaseWorkoutList;
    static ArrayAdapter createWorkoutArrayAdapter;
    DatabaseCreateWorkout databaseCreateWorkout;
    static ListView wList;
    static ArrayList<ExerciseModel> displayList ;


    public createWorkoutFragment() {
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        binding = FragmentCreateWorkoutBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment

        displayList = new ArrayList<>();
        wList = binding.workoutList;

        //display current template on list
        createWorkoutArrayAdapter = new ArrayAdapter<ExerciseModel>(getContext(), android.R.layout.simple_list_item_1,displayList);
        wList.setAdapter(createWorkoutArrayAdapter);

        binding.addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExerciseDialogFragment dialogFragment = new createExerciseDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "    add_ex_dialog");
            }
        });

        binding.saveWorkoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(binding.workoutTitleInput.getText().toString())){
                    Toast.makeText(getContext(), "Please Enter A Workout Title", Toast.LENGTH_LONG).show();
                }
                else {
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    Toast.makeText(requireActivity().getApplicationContext(), "Workout Saved", Toast.LENGTH_SHORT).show();


                    //TODO
                    // fix this so it saves the
                    // listview item to the database

                    manager.popBackStack();
                }

            }
        });
        return binding.getRoot();
    }

    public void openDialog() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class createExerciseDialogFragment extends AppCompatDialogFragment {

        private AlertDialogueBinding binding;

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

                             displayList.add(exerciseModel);

                             createWorkoutArrayAdapter = new ArrayAdapter<ExerciseModel>(getContext(), android.R.layout.simple_list_item_1,displayList);
                             wList.setAdapter(createWorkoutArrayAdapter);
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

    public void saveToWorkoutList() {

    }


}