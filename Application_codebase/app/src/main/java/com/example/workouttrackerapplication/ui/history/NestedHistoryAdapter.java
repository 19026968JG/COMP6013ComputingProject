package com.example.workouttrackerapplication.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutExerciseModel;

import java.util.ArrayList;

public class NestedHistoryAdapter extends RecyclerView.Adapter<NestedHistoryAdapter.NestedHistoryViewholder> {
    private Context context;
    private ArrayList<ActiveWorkoutExerciseModel> completedSets;

    public NestedHistoryAdapter(Context context, ArrayList<ActiveWorkoutExerciseModel> completedSets) {
        this.context=context;
        this.completedSets=completedSets;
    }
    @NonNull
    @Override
    public NestedHistoryAdapter.NestedHistoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);

        View view = inflater.inflate((R.layout.history_nested_card), parent, false);


        return new NestedHistoryAdapter.NestedHistoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedHistoryAdapter.NestedHistoryViewholder holder, int position) {
        ActiveWorkoutExerciseModel model = completedSets.get(position);

        NestedHistoryViewholder viewholder = (NestedHistoryViewholder) holder;

        holder.previousSets.setText(model.getExerciseName()+ " x " + model.getSets() + " @ " + model.getWeight() + "kg");


    }

    @Override
    public int getItemCount() {
        return completedSets.size();
    }

    public static class NestedHistoryViewholder extends RecyclerView.ViewHolder {

        private TextView previousSets;
        public NestedHistoryViewholder(@NonNull View itemView) {
            super(itemView);

            previousSets = itemView.findViewById(R.id.previousSetsCompleted);

        }
    }
}
