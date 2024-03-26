package com.example.workouttrackerapplication.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.example.workouttrackerapplication.ExerciseModel;
import com.example.workouttrackerapplication.ui.active.ActiveWorkoutExerciseModel;
import com.example.workouttrackerapplication.ui.history.HistoryDisplayModel;

import java.util.ArrayList;

public class DatabaseSavedWorkouts extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WORKOUT_DATABASE";

    // USER TABLE
    private static final String USER_TABLE_NAME = "USER_TABLE";
    private static final String USER_ID = "USER_ID"; // PRIMARY KEY
    private static final String USERNAME = "USERNAME";

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

    private static final String WORKOUT_HISTORY_ITEM_TABLE_NAME = "WORKOUT_HISTORY_ITEM_TABLE";
    private static final String WORKOUT_HISTORY_ITEM_ID = "WORKOUT_HISTORY_ITEM_ID"; // PRIMARY KEY


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
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_HISTORY_ITEM_TABLE_NAME);

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
                + " FOREIGN KEY ( " + EXERCISE_ID + " ) REFERENCES " + EXERCISES_TABLE_NAME
                + "(" + EXERCISE_ID + "), "
                + " FOREIGN KEY ( " + WORKOUT_ID + " ) REFERENCES " + WORKOUTS_TABLE_NAME
                + "(" + WORKOUT_ID + ")"+ ")";

        // CREATE HISTORY TABLE
        String createWorkoutHistoryTable = "CREATE TABLE " + WORKOUT_HISTORY_TABLE_NAME + "("
                + WORKOUT_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE_TIME + " TEXT, "
                + WORKOUT_NAME + " TEXT NOT NULL " + ")";

        //CREATE HISTORY ITEM TABLE
        String createWorkoutHistoryItemTable = "CREATE TABLE " + WORKOUT_HISTORY_ITEM_TABLE_NAME + "("
                + WORKOUT_HISTORY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WORKOUT_HISTORY_ID + " INTEGER , "
                + EXERCISE_NAME + " TEXT NOT NULL, "
                + SETS + " INTEGER, "
                + REPS + " INTEGER, "
                + WEIGHT + " DOUBLE, "
                + " FOREIGN KEY ( " + WORKOUT_HISTORY_ID + " ) REFERENCES " + WORKOUT_HISTORY_TABLE_NAME
                + "(" + WORKOUT_HISTORY_ID + ")" + ")";


        // EXECUTE STATEMENTS
        db.execSQL(createUsersTable);
        db.execSQL(createWorkoutsTable);
        db.execSQL(createExercisesTable);
        db.execSQL(createExerciseValuesTable);
        db.execSQL(createWorkoutHistoryTable);
        db.execSQL(createWorkoutHistoryItemTable);
        db.execSQL("PRAGMA foreign_keys=ON;");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_VALUES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_HISTORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_HISTORY_ITEM_TABLE_NAME);

        onCreate(db);
    }

    public boolean addToUsersTable(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USERNAME, username);

        long insert = db.insert(USER_TABLE_NAME, null, cv);

        return insert != -1;
    }
    public boolean addToWorkoutHistoryTable(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
        String dateString = simpleDateFormat.format(date);

        cv.put(DATE_TIME, dateString);
        cv.put(WORKOUT_NAME, name);

        long insert = db.insert(WORKOUT_HISTORY_TABLE_NAME, null, cv);

        return insert != -1;
    }

    public boolean addToWorkoutHistoryItemTable(ActiveWorkoutExerciseModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int workoutHistoryId = getHistoryId();
            cv.put(WORKOUT_HISTORY_ID, workoutHistoryId);
            cv.put(EXERCISE_NAME, model.getExerciseName());
            cv.put(SETS, model.getSets());
            cv.put(REPS, model.getReps());
            cv.put(WEIGHT, model.getWeight());


        long insert = db.insert(WORKOUT_HISTORY_ITEM_TABLE_NAME,null, cv);
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

    public int getHistoryId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + WORKOUT_HISTORY_ID + ") FROM " + WORKOUT_HISTORY_TABLE_NAME, null);
        int historyId = -1;
        if (cursor.moveToFirst()) {
            historyId = cursor.getInt(0);
        }
        cursor.close();
        return historyId;
    }

    @SuppressLint("Range")
    public int getWorkoutIdFromName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        int workoutId = -1;

        Cursor cursor = db.rawQuery("SELECT " + WORKOUT_ID + " FROM " + WORKOUTS_TABLE_NAME
                + " WHERE " + WORKOUT_NAME + "=?", new String[]{name});

        if (cursor.moveToFirst()) {

            workoutId = cursor.getInt(cursor.getColumnIndex(WORKOUT_ID));
        }
        cursor.close();
        return workoutId;
    }

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

    public ArrayList<ActiveWorkoutExerciseModel> getAllExercisesForWorkout(int workoutId) {
        ArrayList<ActiveWorkoutExerciseModel> exercises = new ArrayList<>();
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


        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String exerciseName = cursor.getString(cursor.getColumnIndex(EXERCISE_NAME));
                @SuppressLint("Range") int sets = cursor.getInt(cursor.getColumnIndex(SETS));
                @SuppressLint("Range") String weight = cursor.getString(cursor.getColumnIndex(WEIGHT));
                @SuppressLint("Range") String reps = cursor.getString(cursor.getColumnIndex(REPS));
                exercises.add(new ActiveWorkoutExerciseModel(exerciseName, sets, weight, reps, false));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return exercises;
    }

    public ArrayList<HistoryDisplayModel> getAllHistory() {
            //TODO HAVE FUNCTION RETURN HISTORY

        ArrayList<HistoryDisplayModel> history = new ArrayList<>();
        ArrayList<ActiveWorkoutExerciseModel> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String sqlQuery = " SELECT " + WORKOUT_HISTORY_TABLE_NAME + "." + WORKOUT_NAME + ","
                +WORKOUT_HISTORY_TABLE_NAME + "." + DATE_TIME
//                + WORKOUT_HISTORY_ITEM_TABLE_NAME + "." + EXERCISE_NAME + ","
//                + WORKOUT_HISTORY_ITEM_TABLE_NAME + "." + SETS + ","
//                + WORKOUT_HISTORY_ITEM_TABLE_NAME + "." + REPS + ","
//                + WORKOUT_HISTORY_ITEM_TABLE_NAME + "." + WEIGHT
                + " FROM " + WORKOUT_HISTORY_TABLE_NAME;
//                + " INNER JOIN " + WORKOUT_HISTORY_ITEM_TABLE_NAME
//                + " ON " + WORKOUT_HISTORY_TABLE_NAME + "." + WORKOUT_HISTORY_ID
//                + " = " + WORKOUT_HISTORY_ITEM_TABLE_NAME + "." + WORKOUT_HISTORY_ID;

        Cursor cursor = db.rawQuery(sqlQuery,null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String workoutName = cursor.getString(cursor.getColumnIndex(WORKOUT_NAME));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(DATE_TIME));
//                @SuppressLint("Range") String exerciseName = cursor.getString(cursor.getColumnIndex(EXERCISE_NAME));
//                @SuppressLint("Range") int sets = cursor.getInt(cursor.getColumnIndex(SETS));
//                @SuppressLint("Range") String weight = cursor.getString(cursor.getColumnIndex(WEIGHT));
//                @SuppressLint("Range") String reps = cursor.getString(cursor.getColumnIndex(REPS));

//                exercises.add(new ActiveWorkoutExerciseModel(exerciseName,sets,weight,reps,false));


                history.add(new HistoryDisplayModel(workoutName, date));

            } while (cursor.moveToNext());
        }
        cursor.close();

//        for (HistoryDisplayModel item :
//                history) {
//            System.out.println(item.toString() + "\n" + item.getExerciseList().toString());
//        }

        return history;
    }

    @SuppressLint("Range")
    public String getUsernameFromWorkoutName(String name) {
        ArrayList<ActiveWorkoutExerciseModel> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = "";

        String sqlQuery = "SELECT " + USER_TABLE_NAME + "." + USERNAME
                + " FROM " + USER_TABLE_NAME
                + " INNER JOIN " + WORKOUTS_TABLE_NAME
                + " ON " + WORKOUTS_TABLE_NAME + "." + USER_ID
                + " = " + USER_TABLE_NAME + "." + USER_ID
                + " WHERE " + WORKOUTS_TABLE_NAME + "." + WORKOUT_NAME + "=" + "=?" ;


        Cursor cursor = db.rawQuery(sqlQuery,new String[]{name});

        if(cursor.moveToFirst()) {
            do {
                userName = cursor.getString(cursor.getColumnIndex(USERNAME));
            } while (cursor.moveToNext());
        }
        cursor.close();

         return userName;
    }

}
