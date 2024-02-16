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

    // USER TABLE
    private static final String USER_TABLE_NAME = "USER_TABLE";
    private static final String USER_ID = "USER_ID"; // PRIMARY KEY
    private static final String USER_FIRST_NAME = "USER_FIRST_NAME";
    private static final String USER_LAST_NAME = "USER_LAST_NAME";


    // WORKOUTS TABLE
    private static final String WORKOUTS_TABLE_NAME = "WORKOUTS_TABLE";
    private static final String WORKOUT_ID = "WORKOUT_ID"; // PRIMARY KEY
    private static final String WORKOUT_NAME = "WORKOUT_NAME";


    // EXERCISE TABLE
    private static final String EXERCISES_TABLE_NAME = "EXERCISES_TABLE";
    private static final String EXERCISE_ID = "EXERCISES_ID"; // PRIMARY KEY
    private static final String EXERCISE_NAME = "EXERCISE_NAME";

    // WORKOUT EXERCISE TABLE
    private static final String EXERCISE_VALUES_TABLE_NAME = "EXERCISE_VALUES_TABLE";
    private static final String REPS = "REPS";
    private static final String SETS = "SETS";
    private static final String WEIGHT = "WEIGHT";

    // FOREIGN KEYS
    private static final String USER_FK_WORKOUTS_TABLE = "USER_ID";
    private static final String EXERCISE_FK_EXERCISE_VALUES_TABLE = "EXERCISE_FK_EXERCISE_VALUES_TABLE";
    private static final String WORKOUT_FK_EXERCISE_VALUES_TABLE = "WORKOUT_FK_EXERCISE_VALUES_TABLE";

    public DatabaseSavedWorkouts(@Nullable Context context) {
        super(context, "create_workout_db.db", null, 1);
    }
    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CLEAR DB IF TABLES ALREADY EXIST
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_VALUES_TABLE_NAME);

        // CREATE USERS TABLE
        String createUsersTable = "CREATE TABLE " + USER_TABLE_NAME + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_FIRST_NAME + " TEXT, "
                + USER_LAST_NAME + " TEXT " + ")";

        // CREATE WORKOUTS TABLE
        String createWorkoutsTable = "CREATE TABLE " + WORKOUTS_TABLE_NAME + "("
                + WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_NAME + " TEXT, "
                + USER_FK_WORKOUTS_TABLE + " INTEGER, "
                + " FOREIGN KEY ( " + USER_FK_WORKOUTS_TABLE + " ) REFERENCES " + USER_TABLE_NAME + "(" + USER_ID + ")); ";

        // CREATE EXERCISES TABLE
        String createExercisesTable = "CREATE TABLE " + EXERCISES_TABLE_NAME + "("
                + EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EXERCISE_NAME + " TEXT " + ")" ;

        //CREATE WORKOUT EXERCISES TABLE
        String createExerciseValuesTable = "CREATE TABLE " + EXERCISE_VALUES_TABLE_NAME + "("
                + EXERCISE_FK_EXERCISE_VALUES_TABLE + " INTEGER, "
                + WORKOUT_FK_EXERCISE_VALUES_TABLE + " INTEGER, "
                + SETS + " INTEGER, "
                + REPS + " INTEGER, "
                + WEIGHT + " DOUBLE, "
                + " FOREIGN KEY ( " + EXERCISE_FK_EXERCISE_VALUES_TABLE + " ) REFERENCES " + EXERCISES_TABLE_NAME + "(" + EXERCISE_ID + "), "
                + " FOREIGN KEY ( " + WORKOUT_FK_EXERCISE_VALUES_TABLE + " ) REFERENCES " + WORKOUTS_TABLE_NAME + "(" + WORKOUT_ID + ")"+ ")";

        // EXECUTE STATEMENTS
        db.execSQL(createUsersTable);
        db.execSQL(createWorkoutsTable);
        db.execSQL(createExercisesTable);
        db.execSQL(createExerciseValuesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addToUsersTable() {
        return true;
    }
    public boolean addToExerciseValuesTable(ExerciseModel exerciseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(REPS, exerciseModel.getNumOfReps());
        cv.put(SETS, exerciseModel.getNumOfSets());
        cv.put(WEIGHT, exerciseModel.getWeight());

        // PUT FOREIGN KEY IN WORKOUT EXERCISE TABLE
        cv.put(EXERCISE_FK_EXERCISE_VALUES_TABLE, EXERCISE_ID);
        cv.put(WORKOUT_FK_EXERCISE_VALUES_TABLE, WORKOUT_ID);

        long insert = db.insert(EXERCISE_VALUES_TABLE_NAME, null, cv);

        return insert != -1;
    }
    public boolean addToExerciseTable(ExerciseModel exerciseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXERCISE_NAME, exerciseModel.getExName());

        long insert = db.insert(EXERCISES_TABLE_NAME, null, cv);

        return insert != -1;
    }
    public boolean addToWorkoutTable(WorkoutModel workoutModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(WORKOUT_NAME, workoutModel.getWorkoutName());

        long insert = db.insert(WORKOUTS_TABLE_NAME, null, cv);

        return insert != -1;
    }

    public List<ExerciseModel> getAllExercises(){
      List<ExerciseModel> returnList = new ArrayList<>();

      String queryString = "SELECT * FROM " + EXERCISES_TABLE_NAME + " JOIN " + EXERCISE_VALUES_TABLE_NAME;

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
            int fk_id = cursor.getInt(5);

            ExerciseModel newExercise = new ExerciseModel(exerciseID, exerciseName, sets, reps, weight, fk_id);
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
