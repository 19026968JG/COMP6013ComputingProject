package com.example.workouttrackerapplication.ui.create_workout;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.ExerciseModel;
import com.example.workouttrackerapplication.databinding.FragmentCreateWorkoutBinding;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutFragment;
import com.example.workouttrackerapplication.ui.workouts.WorkoutsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class CreateWorkoutFragment extends Fragment {

    private FragmentCreateWorkoutBinding binding;
    DatabaseSavedWorkouts databaseSavedWorkouts;
//    static ListView wList;
    static ArrayList<ExerciseModel> displayList;
    private Button addExButton;

    static ArrayAdapter createWorkoutArrayAdapter;

    public CreateWorkoutFragment() {
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        binding = FragmentCreateWorkoutBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment

        View footer = inflater.inflate(R.layout.add_exercise_cancel_create_workout_buttons,null);
        addExButton = footer.findViewById(R.id.add_exercise_button);


        displayList = new ArrayList<>();
        ListView wList = binding.workoutList;
        databaseSavedWorkouts = new DatabaseSavedWorkouts(getContext());
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);

        createWorkoutArrayAdapter = new ArrayAdapter<>(wList.getContext(), android.R.layout.simple_list_item_1, displayList);
        wList.setAdapter(createWorkoutArrayAdapter);


        binding.addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateExerciseDialogFragment dialogFragment = new CreateExerciseDialogFragment();
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "    add_ex_dialog");
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


                    Toast.makeText(requireActivity().getApplicationContext(), "Workout Saved", Toast.LENGTH_SHORT).show();
                    try {

                        databaseSavedWorkouts.addToWorkoutTable(binding.workoutTitleInput.getText().toString());
                        for(int i=0; i<displayList.size();i++) {
                            databaseSavedWorkouts.addToExerciseTable(displayList.get(i));
                            databaseSavedWorkouts.addToExerciseValuesTable(displayList.get(i));
                        }

                    }catch (Exception e){
                        Toast.makeText(getContext(),"Error Adding Workout", Toast.LENGTH_SHORT).show();
                    }
                    navBar.setVisibility(View.VISIBLE);
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.workouts_page, new WorkoutsFragment());
                    transaction.commit();


                }
            }
        });

        // DELETE EXERCISE FROM WORKOUT
        binding.workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(requireContext())

                        .setTitle("Remove " + displayList.get(position).getExName().toString() + " From Workout?")
                        .setPositiveButton("Yes", (dialog,which) -> {

                            displayList.remove(position);
                            createWorkoutArrayAdapter.notifyDataSetChanged();

                        }).setNegativeButton("No", (dialog,which)-> {
                                dialog.dismiss();
                        }).create().show();
            }
        });

        binding.cancelBuildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatingActionButton floatingActionButton = requireActivity().findViewById(R.id.fab_add_workout_button);
                FragmentManager manager = requireActivity().getSupportFragmentManager();

                floatingActionButton.show();
                navBar.setVisibility(View.VISIBLE);
                manager.popBackStack();

            }
        });

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        System.gc();
    }
}
