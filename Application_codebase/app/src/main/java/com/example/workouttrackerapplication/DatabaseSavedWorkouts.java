package com.example.workouttrackerapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSavedWorkouts extends SQLiteOpenHelper {
    //workouts table
    private static final String SAVED_WORKOUTS_TABLE = "SAVED_WORKOUTS_TABLE";
    private static final String COLUMN_ID_WORKOUTS = "WORKOUTS_ID"; // primary key
    private static final String COLUMN_WORKOUT_NAME = "WORKOUT_NAME";

    //exercise table
    private static final String SAVED_EXERCISES_TABLE = "EXERCISE_TABLE_NAME";
    private static final String COLUMN_ID_EXERCISES = "EXERCISES_ID";
    private static final String COLUMN_EXERCISE_NAME = "EXERCISE_NAME";
    private static final String COLUMN_REPS = "REPS";
    private static final String COLUMN_SETS = "SETS";
    private static final String COLUMN_WEIGHT = "WEIGHT";
    private static final String FK_WORKOUT_ID = "FK_WORKOUT_ID"; // foreign key reference for child table

    public DatabaseSavedWorkouts(@Nullable Context context) {
        super(context, "create_workout_db.db", null, 1);
    }
    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Clear db so a new workout can be created
        db.execSQL("DROP TABLE IF EXISTS " + SAVED_EXERCISES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SAVED_WORKOUTS_TABLE);

        // Create the SAVED_WORKOUTS_TABLE
        String createWorkoutsDB = "CREATE TABLE " + SAVED_WORKOUTS_TABLE + "("
                + COLUMN_ID_WORKOUTS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WORKOUT_NAME + " TEXT ) ";
        db.execSQL(createWorkoutsDB);

        // Create the SAVED_EXERCISES_TABLE with a foreign key reference
        String createExerciseDB = "CREATE TABLE " + SAVED_EXERCISES_TABLE + "("
                + COLUMN_ID_EXERCISES + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EXERCISE_NAME + " TEXT, "
                + COLUMN_REPS + " INT, "
                + COLUMN_SETS + " INT, "
                + COLUMN_WEIGHT + " FLOAT, "
                + FK_WORKOUT_ID + " INTEGER, "
                + " FOREIGN KEY ( " + FK_WORKOUT_ID + " ) REFERENCES "
                + COLUMN_WORKOUT_NAME + " ( " + COLUMN_ID_WORKOUTS + " )) ";
        db.execSQL(createExerciseDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addExerciseToChildTable(ExerciseModel exerciseModel, String parentName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXERCISE_NAME, exerciseModel.getExName());
        cv.put(COLUMN_REPS, exerciseModel.getNumOfReps());
        cv.put(COLUMN_SETS, exerciseModel.getNumOfSets());
        cv.put(COLUMN_WEIGHT, exerciseModel.getWeight());

        long insert = db.insert(parentName, null, cv);

        return insert != -1;
    }

    public boolean addNewWorkoutToParentTable(WorkoutModel workoutModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORKOUT_NAME, workoutModel.getWorkoutName());



        long insert = db.insert(SAVED_WORKOUTS_TABLE, null, cv);

        return insert != -1;
    }

    public List<ExerciseModel> getAllExercises(){
      List<ExerciseModel> returnList = new ArrayList<>();

      String queryString = "SELECT * FROM " + SAVED_EXERCISES_TABLE;

      SQLiteDatabase db = this.getReadableDatabase();

      Cursor cursor = db.rawQuery(queryString, null);
      if (cursor.moveToFirst()) {
          //loop through cursor and create new objects. put them into the return list
        do{
            int exerciseID = cursor.getInt(0);
            String exerciseName = cursor.getString(1);
            int sets = cursor.getInt(2);
            int reps = cursor.getInt(3);
            double weight = cursor.getDouble(4);

            ExerciseModel newExercise = new ExerciseModel(exerciseID, exerciseName, sets, reps, weight);
            returnList.add(newExercise);

        }while(cursor.moveToNext());

      }else{
            //failure do not add
      }
      cursor.close();
      db.close();
      return returnList;
    }

}
