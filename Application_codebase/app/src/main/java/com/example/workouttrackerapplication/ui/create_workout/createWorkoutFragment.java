package com.example.workouttrackerapplication.ui.create_workout;
import com.example.workouttrackerapplication.databinding.FragmentCreateWorkoutBinding;
import com.example.workouttrackerapplication.R;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;



public class createWorkoutFragment extends Fragment {

private FragmentCreateWorkoutBinding binding;

    public createWorkoutFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        binding = FragmentCreateWorkoutBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment

        binding.addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        binding.saveWorkoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                Toast.makeText(requireActivity().getApplicationContext(), "Workout Saved", Toast.LENGTH_SHORT).show();
                manager.popBackStack();

            }
        });
        return binding.getRoot();
    }

    public void openDialog() {
        createExerciseDialogFragment dialogFragment = new createExerciseDialogFragment();
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "    add_ex_dialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        Context context;
        List<exerciseItem> items;

        public MyAdapter(Context context, List<exerciseItem> items) {
            this.context = context;
            this.items = items;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_view, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.nameView.setText(items.get(position).getExerciseName());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.exercise_name);
        }
    }

}