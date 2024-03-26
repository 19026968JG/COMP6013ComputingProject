package com.example.workouttrackerapplication.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutExerciseModel;

import java.util.ArrayList;

public class HistoryDisplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<HistoryDisplayModel> previousWorkouts;

    public HistoryDisplayAdapter(Context context, ArrayList<HistoryDisplayModel> previousWorkouts) {
        this.context = context;
        this.previousWorkouts = previousWorkouts;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_display_card,parent,false);


        return new MyHistoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryDisplayModel model = previousWorkouts.get(position);
        MyHistoryViewHolder viewHolder = (MyHistoryViewHolder) holder;
        viewHolder.workoutName.setText(model.getWorkoutName() + " completed on: " + model.getDate());

//        NestedHistoryAdapter nestedHistoryAdapter = new NestedHistoryAdapter(context,model.getExerciseList());
//        viewHolder.nestedRv.setLayoutManager(new LinearLayoutManager(context));
//        viewHolder.nestedRv.setAdapter(nestedHistoryAdapter);
    }

    @Override
    public int getItemCount() {
        return previousWorkouts.size();
    }



    public static class MyHistoryViewHolder extends RecyclerView.ViewHolder{

        private TextView workoutName;
        private RecyclerView nestedRv;

        public MyHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            workoutName = itemView.findViewById(R.id.historyWorkoutName);
            nestedRv = itemView.findViewById(R.id.nestedHistoryRecyclerView);
        }
    }
}
