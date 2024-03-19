package com.example.workouttrackerapplication.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.workouttrackerapplication.ExerciseModel;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutExerciseNameModel;

import java.util.ArrayList;

public class DatabaseSavedWorkouts extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WORKOUT_DATABASE";

    // USER TABLE
    private static final String USER_TABLE_NAME = "USER_TABLE";
    private static final String USER_ID = "USER_ID"; // PRIMARY KEY
    private static final String USERNAME = "USERNAME";
    private static final String USER_LAST_NAME = "USER_LAST_NAME";


    // WORKOUTS TABLE VALUES
    private static final String WORKOUTS_TABLE_NAME = "WORKOUTS_TABLE";
    private static final String WORKOUT_ID = "WORKOUT_ID"; // PRIMARY KEY
    private static final String WORKOUT_NAME = "WORKOUT_NAME";


    // EXERCISE TABLE VALUES
    private static final String EXERCISES_TABLE_NAME = "EXERCISES_TABLE";
    private static final String EXERCISE_ID = "EXERCISES_ID"; // PRIMARY KEY
    private static final String EXERCISE_NAME = "EXERCISE_NAME";

    // WORKOUT EXERCISE TABLE VALUES
    private static final String EXERCISE_VALUES_TABLE_NAME = "EXERCISE_VALUES_TABLE";
    private static final String REPS = "REPS";
    private static final String SETS = "SETS";
    private static final String WEIGHT = "WEIGHT";

    // WORKOUT HISTORY TABLE VALUES
    private static final String WORKOUT_HISTORY_TABLE_NAME = "WORKOUT_HISTORY_TABLE";
    private static final String WORKOUT_HISTORY_ID = "WORKOUT_HISTORY_ID"; // PRIMARY KEY
    private static final String DATE_TIME = "DATE_TIME";


    public DatabaseSavedWorkouts(@Nullable Context context) {
        super(context, "create_workout_db.db", null, DATABASE_VERSION);
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
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_HISTORY_TABLE_NAME);

        // CREATE USERS TABLE
        String createUsersTable = "CREATE TABLE " + USER_TABLE_NAME + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT " + ")";

        // CREATE WORKOUTS TABLE
        String createWorkoutsTable = "CREATE TABLE " + WORKOUTS_TABLE_NAME + "("
                + WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_NAME + " TEXT NOT NULL, "
                + USER_ID + " INTEGER, "
                + " FOREIGN KEY ( " + USER_ID + " ) REFERENCES " + USER_TABLE_NAME + "(" + USER_ID + ")); ";

        // CREATE EXERCISES TABLE
        String createExercisesTable = "CREATE TABLE " + EXERCISES_TABLE_NAME + "("
                + EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EXERCISE_NAME + " TEXT NOT NULL " + ")" ;

        //CREATE WORKOUT EXERCISES TABLE
        String createExerciseValuesTable = "CREATE TABLE " + EXERCISE_VALUES_TABLE_NAME + "("
                + EXERCISE_ID + " INTEGER NOT NULL, "
                + WORKOUT_ID + " INTEGER NOT NULL, "
                + SETS + " INTEGER, "
                + REPS + " INTEGER, "
                + WEIGHT + " DOUBLE, "
                + " PRIMARY KEY (" + EXERCISE_ID + ", " + WORKOUT_ID + "),"
                + " FOREIGN KEY ( " + EXERCISE_ID + " ) REFERENCES " + EXERCISES_TABLE_NAME + "(" + EXERCISE_ID + "), "
                + " FOREIGN KEY ( " + WORKOUT_ID + " ) REFERENCES " + WORKOUTS_TABLE_NAME + "(" + WORKOUT_ID + ")"+ ")";

        // EXECUTE STATEMENTS
        db.execSQL(createUsersTable);
        db.execSQL(createWorkoutsTable);
        db.execSQL(createExercisesTable);
        db.execSQL(createExerciseValuesTable);
        db.execSQL("PRAGMA foreign_keys=ON;");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_VALUES_TABLE_NAME);

        onCreate(db);
    }

    public boolean addToUsersTable(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USERNAME, username);

        long insert = db.insert(USER_TABLE_NAME, null, cv);

        return insert != -1;
    }

    public boolean checkDatabaseExists(Context context) {
        String path = context.getDatabasePath("create_workout_db.db").getPath();
        return new java.io.File(path).exists();
    }

    public boolean checkEmptyUsersTable() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME) == 0;
    }
    public boolean addToExerciseValuesTable(ExerciseModel exerciseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int exerciseId = getExerciseId();
        int workoutId = getWorkoutId();

        // PUT FOREIGN KEY IN EXERCISE VALUES TABLE
        cv.put(EXERCISE_ID, exerciseId);
        cv.put(WORKOUT_ID, workoutId);
        // PUT REST OF DATA IN TABLE
        cv.put(REPS, exerciseModel.getNumOfReps());
        cv.put(SETS, exerciseModel.getNumOfSets());
        cv.put(WEIGHT, exerciseModel.getWeight());



        long insert = db.insert(EXERCISE_VALUES_TABLE_NAME, null, cv);

        return insert != -1;
    }
    public boolean addToExerciseTable(ExerciseModel exerciseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXERCISE_NAME, exerciseModel.getExName().toUpperCase());

        long insert = db.insert(EXERCISES_TABLE_NAME, null, cv);

        return insert != -1;
    }
    public boolean addToWorkoutTable(String workoutName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int userId = getUserId();

        cv.put(USER_ID, userId);
        cv.put(WORKOUT_NAME, workoutName);

        long insert = db.insert(WORKOUTS_TABLE_NAME, null, cv);

        return insert != -1;
    }

    private int getUserId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + USER_ID + ") FROM " + USER_TABLE_NAME, null);
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }

    // Method to get the latest exercise ID from the EXERCISES_TABLE
    private int getExerciseId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + EXERCISE_ID + ") FROM " + EXERCISES_TABLE_NAME, null);
        int exerciseId = -1;
        if (cursor.moveToFirst()) {
            exerciseId = cursor.getInt(0);
        }
        cursor.close();
        return exerciseId;
    }

    // Method to get the latest workout ID from the WORKOUTS_TABLE
    public int getWorkoutId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + WORKOUT_ID + ") FROM " + WORKOUTS_TABLE_NAME, null);
        int workoutId = -1;
        if (cursor.moveToFirst()) {
            workoutId = cursor.getInt(0);
        }
        cursor.close();
        return workoutId;
    }

    @SuppressLint("Range")
    public int getWorkoutIdFromName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        int workoutId = -1;

        // Using selectionArgs to prevent SQL injection and to correctly handle string values
        Cursor cursor = db.rawQuery("SELECT " + WORKOUT_ID + " FROM " + WORKOUTS_TABLE_NAME
                + " WHERE " + WORKOUT_NAME + "=?", new String[]{name});

        if (cursor.moveToFirst()) {
            // Fetch the workout ID from the cursor
            workoutId = cursor.getInt(cursor.getColumnIndex(WORKOUT_ID));
        }
        cursor.close();
        return workoutId;
    }

    // Method to get the latest workout NAME from the WORKOUTS_TABLE
    public String getWorkoutName() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + WORKOUT_NAME +") FROM " + WORKOUTS_TABLE_NAME, null);

        String workoutName = "";
        if (cursor.moveToFirst()) {
            workoutName = cursor.getString(0);
        }
        cursor.close();
        return workoutName;
    }

    public ArrayList<String> getAllWorkoutNames() {
        ArrayList<String> workoutListDisplay = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String sqlQuery = " SELECT " + WORKOUTS_TABLE_NAME + "." + WORKOUT_NAME + " FROM " + WORKOUTS_TABLE_NAME;

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String workoutName = cursor.getString(cursor.getColumnIndex(WORKOUT_NAME));
                workoutListDisplay.add(workoutName);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return workoutListDisplay;
    }

    public ArrayList<ActiveWorkoutExerciseNameModel> getAllExercisesForWorkout(int workoutId) {
        ArrayList<ActiveWorkoutExerciseNameModel> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = "SELECT " + EXERCISES_TABLE_NAME + "." + EXERCISE_NAME + ","
                + EXERCISE_VALUES_TABLE_NAME + "." + SETS + ", "
                + EXERCISE_VALUES_TABLE_NAME + "." + WEIGHT + ", "
                + EXERCISE_VALUES_TABLE_NAME + "." + REPS
                + " FROM " + EXERCISES_TABLE_NAME
                + " INNER JOIN " + EXERCISE_VALUES_TABLE_NAME
                + " ON " + EXERCISES_TABLE_NAME + "." + EXERCISE_ID
                + " = " + EXERCISE_VALUES_TABLE_NAME + "." + EXERCISE_ID
                + " WHERE " + EXERCISE_VALUES_TABLE_NAME + "." + WORKOUT_ID + " = " + workoutId;


        Cursor cursor = db.rawQuery(sqlQuery,null);

        if(cursor.moveToFirst()) {
            do {
               @SuppressLint("Range") String exerciseName = cursor.getString(cursor.getColumnIndex(EXERCISE_NAME));
               @SuppressLint("Range") int sets = cursor.getInt(cursor.getColumnIndex(SETS));
               @SuppressLint("Range") String weight = cursor.getString(cursor.getColumnIndex(WEIGHT));
               @SuppressLint("Range") String reps = cursor.getString(cursor.getColumnIndex(REPS));
                exercises.add(new ActiveWorkoutExerciseNameModel(exerciseName,sets,weight,reps));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return exercises;
    }
    @SuppressLint("Range")
    public ArrayList<Integer> getNumberOfSets(int workoutId) {
        ArrayList<Integer> setsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = "SELECT " + EXERCISE_VALUES_TABLE_NAME + "." + SETS + ", "
                + " FROM " + EXERCISES_TABLE_NAME
                + " INNER JOIN " + EXERCISE_VALUES_TABLE_NAME
                + " ON " + EXERCISES_TABLE_NAME + "." + EXERCISE_ID
                + " = " + EXERCISE_VALUES_TABLE_NAME + "." + EXERCISE_ID
                + " WHERE " + EXERCISE_VALUES_TABLE_NAME + "." + WORKOUT_ID + " = " + workoutId;
        
        Cursor cursor = db.rawQuery(sqlQuery,null);

        if(cursor.moveToFirst()) {
            do {
                setsList.add(cursor.getInt(cursor.getColumnIndex(SETS)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return setsList;
    }

    @SuppressLint("Range")
    public ArrayList<Float> getAllWeightsForWorkout(int workoutId) {
        ArrayList<Float> weights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = "SELECT " + EXERCISE_VALUES_TABLE_NAME + "." + WEIGHT + ", "
                + " FROM " + EXERCISE_VALUES_TABLE_NAME
                + " INNER JOIN " + EXERCISE_VALUES_TABLE_NAME
                + " ON " + EXERCISES_TABLE_NAME + "." + EXERCISE_ID
                + " = " + EXERCISE_VALUES_TABLE_NAME + "." + EXERCISE_ID
                + " WHERE " + EXERCISE_VALUES_TABLE_NAME + "." + WORKOUT_ID + " = " + workoutId;

        Cursor cursor = db.rawQuery(sqlQuery,null);

        if(cursor.moveToFirst()) {
            do {
                weights.add(cursor.getFloat(cursor.getColumnIndex(WEIGHT)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return weights;
    }

    @SuppressLint("Range")
    public ArrayList<Integer> getAllRepsForWorkout(int workoutId) {
        ArrayList<Integer> weights = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = "SELECT " + EXERCISE_VALUES_TABLE_NAME + "." + REPS + ", "
                + " FROM " + EXERCISE_VALUES_TABLE_NAME
                + " INNER JOIN " + EXERCISE_VALUES_TABLE_NAME
                + " ON " + EXERCISES_TABLE_NAME + "." + EXERCISE_ID
                + " = " + EXERCISE_VALUES_TABLE_NAME + "." + EXERCISE_ID
                + " WHERE " + EXERCISE_VALUES_TABLE_NAME + "." + WORKOUT_ID + " = " + workoutId;

        Cursor cursor = db.rawQuery(sqlQuery,null);

        if(cursor.moveToFirst()) {
            do {
                weights.add(cursor.getInt(cursor.getColumnIndex(REPS)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return weights;
    }

}
