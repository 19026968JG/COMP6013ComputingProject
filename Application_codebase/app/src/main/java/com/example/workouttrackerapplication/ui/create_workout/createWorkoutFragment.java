package com.example.workouttrackerapplication.ui.create_workout;
import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.ExerciseModel;
import com.example.workouttrackerapplication.WorkoutModel;
import com.example.workouttrackerapplication.databinding.FragmentCreateWorkoutBinding;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class createWorkoutFragment extends Fragment {

    FragmentCreateWorkoutBinding binding;
    DatabaseSavedWorkouts databaseSavedWorkouts;
    @SuppressLint("StaticFieldLeak")
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
        databaseSavedWorkouts = new DatabaseSavedWorkouts(getContext());
        //display current template on list

        binding.addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateExerciseDialogFragment dialogFragment = new CreateExerciseDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "    add_ex_dialog");

                ArrayAdapter createWorkoutArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, createWorkoutFragment.displayList);
                wList.setAdapter(createWorkoutArrayAdapter);
            }
        });

        binding.saveWorkoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(binding.workoutTitleInput.getText().toString())){
                    Toast.makeText(getContext(), "Please Enter A Workout Title", Toast.LENGTH_LONG).show();
                }
                if (wList.getCount() == 0){
                    Toast.makeText(getContext(), "You Cannot Save an Empty Workout. \n" +
                            "Please Add One or More Exercises to Save", Toast.LENGTH_LONG).show();
                }
                else {
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    Toast.makeText(requireActivity().getApplicationContext(), "Workout Saved", Toast.LENGTH_SHORT).show();

                    /*
                    //TODO
                    * fix this so it saves the
                    * listview item to the database
                    */

                    WorkoutModel workoutModel = new WorkoutModel(
                            -1,
                            binding.workoutTitleInput.getText().toString());
                    try {
                        databaseSavedWorkouts.addNewWorkoutToParentTable(workoutModel);
                    }catch (Exception e){
                        Toast.makeText(getContext(),"Error Adding Workout", Toast.LENGTH_SHORT).show();
                    }

                    for(int i = 0; i < displayList.size(); i++) {

                        ExerciseModel exerciseModel = new ExerciseModel(
                                displayList.get(i).getId(),
                                displayList.get(i).getExName(),
                                displayList.get(i).getNumOfSets(),
                                displayList.get(i).getNumOfReps(),
                                displayList.get(i).getWeight());


                           databaseSavedWorkouts.addExerciseToChildTable(exerciseModel, workoutModel.getWorkoutName());
                    }
                    manager.popBackStack();
                }
            }
        });

        return binding.getRoot();
    }
    public void updateListView() {
        ArrayAdapter createWorkoutArrayAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, createWorkoutFragment.displayList);
        wList.setAdapter(createWorkoutArrayAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
