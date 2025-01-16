package com.example.quotesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "liked_quotes";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_QUOTES = "quotes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUOTE = "quote";
    public static final String COLUMN_AUTHOR = "author";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_QUOTES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUOTE + " TEXT, " +
                    COLUMN_AUTHOR + " TEXT" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        onCreate(db);
    }
}