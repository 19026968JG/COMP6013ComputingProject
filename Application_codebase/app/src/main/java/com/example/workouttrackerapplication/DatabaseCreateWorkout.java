package com.example.workouttrackerapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseCreateWorkout extends SQLiteOpenHelper {
    public static final String CREATE_WORKOUT_TEMPLATE = "CREATE_WORKOUT_TEMPLATE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_WORKOUT_NAME = "WORKOUT_NAME";
    public static final String COLUMN_REPS = "REPS";
    public static final String COLUMN_SETS = "SETS";
    public static final String COLUMN_WEIGHT = "WEIGHT";

    public DatabaseCreateWorkout(@Nullable Context context) {
        super(context, "create_workout_db.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //clear db so new workout can be created
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WORKOUT_TEMPLATE);

        String createTableStatement = "CREATE TABLE " + CREATE_WORKOUT_TEMPLATE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WORKOUT_NAME + " TEXT, " + COLUMN_REPS + " INT, " + COLUMN_SETS + " INT, "
                + COLUMN_WEIGHT + " FLOAT )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(ExerciseModel exerciseModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORKOUT_NAME, exerciseModel.getExName());
        cv.put(COLUMN_REPS, exerciseModel.getNumOfReps());
        cv.put(COLUMN_SETS, exerciseModel.getNumOfSets());
        cv.put(COLUMN_WEIGHT, exerciseModel.getWeight());

        long insert = db.insert(CREATE_WORKOUT_TEMPLATE, null, cv);

        return insert != -1;
    }

    public List<ExerciseModel> getAllExercises(){
      List<ExerciseModel> returnList = new ArrayList<>();

      String queryString = "SELECT * FROM " + CREATE_WORKOUT_TEMPLATE;

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
