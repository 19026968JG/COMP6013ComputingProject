package com.example.workouttrackerapplication.ui.workouts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.activity.OnBackPressedCallback;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.FragmentWorkoutsBinding;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutFragment;
import com.example.workouttrackerapplication.ui.create_workout.CreateWorkoutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WorkoutsFragment extends Fragment {

    private RecyclerView savedWorkoutsList;
    private FragmentWorkoutsBinding binding;
    private ArrayList<String> workoutsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());
        workoutsList = new ArrayList<>();
        workoutsList = db.getAllWorkoutNames();

        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);

        WorkoutViewAdapter adapter = new WorkoutViewAdapter(getContext(),workoutsList);
        savedWorkoutsList = binding.workoutListView;
        savedWorkoutsList.setLayoutManager(new GridLayoutManager(getContext(),1));
        savedWorkoutsList.setAdapter(adapter);


        // Floating Action Button Functionality
        binding.fabAddWorkoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

               transaction.replace(R.id.workouts_page,  new CreateWorkoutFragment(),null);
                binding.cardView2.setVisibility(View.GONE);
                binding.fabAddWorkoutButton.hide();
                transaction.commit();

            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                String workoutToRemove = workoutsList.get(position);
                DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());

                new AlertDialog.Builder(requireContext())
                        .setTitle("Delete Workout?")
                        .setPositiveButton("Yes", (dialog, which) ->{
                            int id = db.getWorkoutIdFromName(workoutToRemove);
                            db.deleteWorkout(id);
                            workoutsList.remove(position);

                            adapter.notifyItemRemoved(position);

                        })
                        .setNegativeButton("No", (dialog, which) -> adapter.notifyItemChanged(position))
                        .create()
                        .show();

            }
        });
        itemTouchHelper.attachToRecyclerView(savedWorkoutsList);
        adapter.setOnItemClickListener(new WorkoutViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                String selectedWorkoutName = workoutsList.get(position);

                new AlertDialog.Builder(requireContext())

                        .setTitle("Start " + workoutsList.get(position) + "?")
                        .setPositiveButton("Yes", (dialog,which) -> {

                            FragmentManager manager = getChildFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();

                            Bundle wOName = new Bundle();
                            wOName.putString("workoutName", selectedWorkoutName);
                            ActiveWorkoutFragment activeWorkoutFragment = new ActiveWorkoutFragment();
                            activeWorkoutFragment.setArguments(wOName);

                            transaction.replace(R.id.workouts_page, activeWorkoutFragment);
                            transaction.addToBackStack("Active Workout Begin Transaction");
                            binding.fabAddWorkoutButton.hide();
                            binding.cardView2.setVisibility(View.GONE);
                            navBar.setVisibility(View.GONE);
                            transaction.commit();
                        })
                        .setNegativeButton("No", (dialog,which)-> {
                            dialog.dismiss();
                        })
                        .create()
                        .show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}