package com.example.workouttrackerapplication.ui.workouts;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.FragmentWorkoutsBinding;

public class WorkoutsCursorAdapter extends CursorAdapter {
    public WorkoutsCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workouts, parent, false);
    }

    @Override
    public void bindView(View lview, Context context, Cursor cursor) {
        ListView savedWorkoutsList = (ListView) lview.findViewById(R.id.saved_workouts_list);

        String wName = cursor.getString(cursor.getColumnIndexOrThrow("WORKOUT_NAME"));
        String exName = cursor.getString(cursor.getColumnIndexOrThrow("EXERCISE_NAME"));
        String sets = cursor.getString(cursor.getColumnIndexOrThrow("SETS"));
        String reps = cursor.getString(cursor.getColumnIndexOrThrow("REPS"));
        String weight = cursor.getString(cursor.getColumnIndexOrThrow("WEIGHT"));
    }
}
