package com.example.workouttrackerapplication.ui.active;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;

import java.util.ArrayList;

public class ActiveWorkoutAdapter extends RecyclerView.Adapter<ActiveWorkoutAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<DisplayExerciseObject> activeWorkoutModels;


    public ActiveWorkoutAdapter(Context context, ArrayList<DisplayExerciseObject> activeWorkoutModels) {
        this.context =context;
        this.activeWorkoutModels = activeWorkoutModels;
    }

    @NonNull
    @Override
    public ActiveWorkoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate((R.layout.active_workout_exercise_layout_card),parent,false);
        return new ActiveWorkoutAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DisplayExerciseObject model = activeWorkoutModels.get(position);

        ArrayList reps = new ArrayList();
        ArrayList weight = new ArrayList();

        reps = model.getAllReps();
        weight = model.getAllWeights();


        holder.exName.setText(model.getExName());

        NestedActiveWorkoutAdapter nestedActiveWorkoutAdapter = new NestedActiveWorkoutAdapter(context,reps,weight);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        holder.nestedRv.setLayoutManager(layoutManager);
        holder.nestedRv.setAdapter(nestedActiveWorkoutAdapter);

    }
    @Override
    public int getItemCount() {
        return activeWorkoutModels.size();
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

}
