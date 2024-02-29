package com.example.workouttrackerapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSavedWorkouts extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

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

        // CREATE USERS TABLE
        String createUsersTable = "CREATE TABLE " + USER_TABLE_NAME + "("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_FIRST_NAME + " TEXT, "
                + USER_LAST_NAME + " TEXT " + ")";

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

    public boolean addToUsersTable(String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_FIRST_NAME, firstName);
        cv.put(USER_LAST_NAME, lastName);

        long insert = db.insert(USER_TABLE_NAME, null, cv);

        return insert != -1;
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

        cv.put(EXERCISE_NAME, exerciseModel.getExName());

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
    private int getWorkoutId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + WORKOUT_ID + ") FROM " + WORKOUTS_TABLE_NAME, null);
        int workoutId = -1;
        if (cursor.moveToFirst()) {
            workoutId = cursor.getInt(0);
        }
        cursor.close();
        return workoutId;
    }

    public Cursor displayWorkouts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = " SELECT " + WORKOUTS_TABLE_NAME + "." + WORKOUT_NAME + ","
                + EXERCISES_TABLE_NAME + "." + EXERCISE_NAME + ","
                + EXERCISE_VALUES_TABLE_NAME + "." + SETS
                + " FROM " + WORKOUTS_TABLE_NAME
                + " INNER JOIN "
                + EXERCISE_VALUES_TABLE_NAME
                + " ON " + EXERCISE_VALUES_TABLE_NAME + "." + WORKOUT_ID + " = " + WORKOUTS_TABLE_NAME + "." + WORKOUT_ID
                + " INNER JOIN "
                + EXERCISES_TABLE_NAME
                + " ON " + EXERCISE_VALUES_TABLE_NAME + "." + EXERCISE_ID + " = " + EXERCISES_TABLE_NAME + "." + EXERCISE_ID;

        return db.rawQuery(sqlQuery, null);
    }
}
