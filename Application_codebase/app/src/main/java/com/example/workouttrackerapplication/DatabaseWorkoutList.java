package com.example.workouttrackerapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseWorkoutList extends SQLiteOpenHelper {

    public static final String WORKOUT_LIST_NAME = "WORKOUTS_LIST";
    public static final String COLUMN_ID = "ID";
    public static final String EXERCISE_LIST = "ListItems";
    public DatabaseWorkoutList(@Nullable Context context) {
        super(context, "workout_list_db.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + WORKOUT_LIST_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EXERCISE_LIST +" TEXT )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_LIST_NAME);
        onCreate(db);
    }

    public void addListItem(ArrayList<String> listItems) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (int i = 0; i < listItems.size(); i++) {
            Log.e("value inserting==", "" + listItems.get(i));
            values.put(EXERCISE_LIST, listItems.get(i));
            db.insert(WORKOUT_LIST_NAME, null, values);
        }
    }

    Cursor getListItem() {
        String selectQuery = "SELECT * FROM " + WORKOUT_LIST_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery(selectQuery,null);
    }

}
