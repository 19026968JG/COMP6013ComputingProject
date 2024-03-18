//package com.example.workouttrackerapplication.ui.active;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.workouttrackerapplication.R;
//
//import java.util.ArrayList;
//
//public class NestedActiveWorkoutAdapter extends RecyclerView.Adapter<NestedActiveWorkoutAdapter.NestedViewHolder> {
//    private Context context;
//    private ArrayList<ActiveWorkoutExerciseNameModel> activeWorkoutModels;
//
//    public NestedActiveWorkoutAdapter(Context context, ArrayList<ActiveWorkoutExerciseNameModel> activeWorkoutModels) {
//        this.context = context;
//        this.activeWorkoutModels = activeWorkoutModels;
//    }
//
//    @NonNull
//    @Override
//    public NestedActiveWorkoutAdapter.NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater =LayoutInflater.from(context);
//        View view = inflater.inflate((R.layout.active_workout_exercise),parent,false);
//        return new NestedActiveWorkoutAdapter.NestedViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull NestedActiveWorkoutAdapter.NestedViewHolder holder, int position) {
//        ActiveWorkoutExerciseNameModel model = activeWorkoutModels.get(position);
//
//
//        // Set sets and reps
//
//        holder.reps.setText(model.getReps());
//        holder.weight.setText(model.getWeight());
//    }
//
//    @Override
//    public int getItemCount() {
//        return activeWorkoutModels.size();
//    }
//
//    public static class NestedViewHolder extends RecyclerView.ViewHolder {
//        private TextView reps;
//        private TextView weight;
//
//        public NestedViewHolder(@NonNull View itemView) {
//            super(itemView);
//            reps = itemView.findViewById(R.id.set_row_reps);
//            weight = itemView.findViewById(R.id.set_row_weight);
//
//        }
//    }
//
//}
