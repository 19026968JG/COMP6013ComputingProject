package com.example.workouttrackerapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DatabaseWorkoutList extends SQLiteOpenHelper {

    // Parent Database List
    private static final String PARENT_LIST_NAME = "WORKOUTS_LIST";
    private static final String COLUMN_PARENT_ID = "ID";
    private static final String COLUMN_WORKOUT_NAME = "WORKOUT_NAME";

    // Child Database List
    private static final String CHILD_LIST_NAME = "EXERCISE_NAME";
    private final String PARENT_WORKOUT_ID = "ID";
    private final String CHILD_LIST_ID = "ID";
    public static final String COLUMN_REPS = "REPS";
    public static final String COLUMN_SETS = "SETS";
    public static final String COLUMN_WEIGHT = "WEIGHT";

    public DatabaseWorkoutList(@Nullable Context context) {
        super(context, "workout_list_db.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PARENT_TABLE = " CREATE TABLE " + PARENT_LIST_NAME + "("
                + COLUMN_PARENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_WORKOUT_NAME + " TEXT " + " ) ";

        String CREATE_CHILD_TABLE = " CREATE TABLE " + CHILD_LIST_NAME + "("
                + CHILD_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PARENT_WORKOUT_ID + " INTEGER, "
                + CHILD_LIST_NAME + " TEXT,"
                + COLUMN_SETS + " INTEGER, "
                + COLUMN_REPS + " INTEGER, "
                + COLUMN_WEIGHT + " DOUBLE " + ") ";


        db.execSQL(CREATE_PARENT_TABLE);
        db.execSQL(CREATE_CHILD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PARENT_LIST_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHILD_LIST_NAME);

        onCreate(db);
    }

    //TODO
    /**
    public void addListItem(ParentListItem listItems) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (int i = 0; i < listItems.size(); i++) {
            Log.e("value inserting==", "" + listItems.get(i));
            values.put(EXERCISE_LIST, listItems.get(i));
            db.insert(PARENT_LIST_NAME, null, values);
        }
    } **/

    Cursor getListItem() {
        String selectQuery = "SELECT * FROM " + PARENT_LIST_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery(selectQuery,null);
    }

}
