package com.example.workouttrackerapplication.ui.active;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databinding.ActiveWorkoutParentBinding;
import com.example.workouttrackerapplication.ui.workouts.WorkoutsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActiveWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements NestedActiveWorkoutAdapter.CompletedSetsAction {
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private Context context;
    private ArrayList<ActiveWorkoutExerciseModel> activeWorkoutModels;
    protected ArrayList<ActiveWorkoutExerciseModel> completedSets = new ArrayList<>() ;
    private ArrayList<ActiveWorkoutExerciseModel> uncheckedSets = new ArrayList<>() ;
    private DatabaseSavedWorkouts db ;
    private FragmentManager manager;
    protected String workoutName;
    private ActiveWorkoutParentBinding binding;

    public ActiveWorkoutAdapter(Context context, ArrayList<ActiveWorkoutExerciseModel> activeWorkoutModels, FragmentManager manager, String workoutName) {
        this.context = context;
        this.activeWorkoutModels = activeWorkoutModels;
        this.manager = manager;
        this.workoutName = workoutName;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        db = new DatabaseSavedWorkouts(context);

        switch (viewType) {
            case VIEW_TYPE_FOOTER:
                view = inflater.inflate(R.layout.active_workout_buttons, parent, false);
                return new FooterViewHolder(view);
            case VIEW_TYPE_NORMAL:
                view = inflater.inflate(R.layout.active_workout_exercise_layout_card, parent, false);
                return new MyViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ActiveWorkoutExerciseModel model = activeWorkoutModels.get(position);
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.exName.setText(model.getExerciseName());

            ArrayList<ActiveWorkoutExerciseModel> nestedModels = new ArrayList<>();
            for (int i = 0; i < model.getSets(); i++) {
                nestedModels.add(activeWorkoutModels.get(position));
            }

            NestedActiveWorkoutAdapter nestedActiveWorkoutAdapter = new NestedActiveWorkoutAdapter(context, nestedModels, this);
            viewHolder.nestedRv.setLayoutManager(new LinearLayoutManager(context));
            viewHolder.nestedRv.setAdapter(nestedActiveWorkoutAdapter);
        }
        else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            footerViewHolder.cancelButton.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Do You Want To Cancel This Workout? Your Progress Will Not Be Saved!")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.active_workout_fragment, new WorkoutsFragment());
                            transaction.addToBackStack(null);

                            transaction.commit();


                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        }).create().show();
            });

            footerViewHolder.saveButton.setOnClickListener(v -> {
                       if(completedSets.size() > 0 ) {
                           new AlertDialog.Builder(context)
                                   .setTitle("Do You Want To Finish This Workout?")
                                   .setPositiveButton("Yes", (dialog, which) -> {

                                       ArrayList<ActiveWorkoutExerciseModel> reducedList = new ArrayList<>();
                                       reducedList = reduceCompletedDataSet(completedSets);

                                       db.addToWorkoutHistoryTable(workoutName);
                                       DatabaseReference fireDb = FirebaseDatabase
                                               .getInstance("https://workoutdatabaseserver-default-rtdb.europe-west1.firebasedatabase.app/")
                                               .getReference("LeaderboardValues/Users/"+db.getUsernameFromWorkoutName(workoutName));

                                       for (ActiveWorkoutExerciseModel set : reducedList) {
                                           db.addToWorkoutHistoryItemTable(set);

                                           switch (set.getExerciseName()){
                                            case "SQUAT":
                                                updateMaxDBValue(fireDb,"SQUAT",Double.parseDouble(set.getWeight()));
                                                break;
                                            case "DEADLIFT":
                                                updateMaxDBValue(fireDb,"DEADLIFT",Double.parseDouble(set.getWeight()));
                                                break;
                                            case "BENCH":
                                                updateMaxDBValue(fireDb,"BENCH",Double.parseDouble(set.getWeight()));
                                                break;
                                           }
                                       }
                                       FragmentTransaction transaction = manager.beginTransaction();
                                       transaction.replace(R.id.active_workout_fragment, new WorkoutsFragment());
                                       transaction.addToBackStack(null);
                                       transaction.commit();
                                   })
                                   .setNegativeButton("No", (dialog, which) -> {
                                       dialog.dismiss();
                                   })
                                   .create().show();
                       }else{Toast.makeText(v.getContext(), "You Must Complete At Least One Set To Save The Workout!",Toast.LENGTH_LONG).show();}
            });

        }
    }

    private void updateMaxDBValue(DatabaseReference dbRef, String key, double workoutSquatValue) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Double dbValue = snapshot.child(key).getValue(Double.class);
                if(dbValue == null || dbValue < workoutSquatValue) {
                    dbRef.child(key).setValue(workoutSquatValue);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase Update weight error", error.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return activeWorkoutModels.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == activeWorkoutModels.size()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public void onSetCompleted(ActiveWorkoutExerciseModel model) {
        completedSets.add(model);
        for (ActiveWorkoutExerciseModel exmodel:
             completedSets) {
            System.out.println(exmodel.toString());
        }
    }
    @Override
    public void onSetRemoved(ActiveWorkoutExerciseModel model) {
        completedSets.remove(model);
    }

    private ArrayList<ActiveWorkoutExerciseModel> reduceCompletedDataSet(ArrayList<ActiveWorkoutExerciseModel> completedWorkout) {
        ArrayList<ActiveWorkoutExerciseModel> updatedDataList = new ArrayList<>();
        Map<ActiveWorkoutExerciseModel, Integer> setCounts = new HashMap<>();

        for (ActiveWorkoutExerciseModel set : completedWorkout) {
            if (setCounts.containsKey(set)) {
                setCounts.put(set, setCounts.get(set) + 1);
            } else {
                setCounts.put(set, 1);
            }
        }

        for (Map.Entry<ActiveWorkoutExerciseModel, Integer> entry : setCounts.entrySet()) {
            ActiveWorkoutExerciseModel originalSet = entry.getKey();
            int count = entry.getValue();

            ActiveWorkoutExerciseModel reducedSet = new ActiveWorkoutExerciseModel(
                    originalSet.getExerciseName(),
                    count,
                    originalSet.getWeight(),
                    originalSet.getReps(),
                    originalSet.isChecked()
            );
            updatedDataList.add(reducedSet);
        }
        return updatedDataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView exName;
        private RecyclerView nestedRv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exName = itemView.findViewById(R.id.exerciseRowHeader);
            nestedRv = itemView.findViewById(R.id.nestedRecyclerViewActicveWorkout);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private Button cancelButton, saveButton;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            cancelButton = itemView.findViewById(R.id.active_workout_cancel_button);
            saveButton = itemView.findViewById(R.id.active_workout_save_button);
        }
    }


}
