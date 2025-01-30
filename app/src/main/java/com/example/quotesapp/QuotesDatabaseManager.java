package com.example.quotesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class QuotesDatabaseManager {
    private final DatabaseHelper dbHelper;
    private final SQLiteDatabase database;

    public QuotesDatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<Quote> getAllQuotes() {
        ArrayList<Quote> quotes = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_QUOTES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String quote = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QUOTE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR));
                quotes.add(new Quote(quote, author));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return quotes;
    }

    public void addLikedQuote(String quote, String author) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_QUOTE, quote);
        values.put(DatabaseHelper.COLUMN_AUTHOR, author);
        database.insert(DatabaseHelper.TABLE_QUOTES, null, values);
    }

    public boolean quoteExists(String quote) {
        String[] columns = {DatabaseHelper.COLUMN_ID};
        String selection = DatabaseHelper.COLUMN_QUOTE + " = ?";
        String[] selectionArgs = {quote};
        Cursor cursor = database.query(DatabaseHelper.TABLE_QUOTES, columns, selection, selectionArgs, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void removeQuote(String quote) {
        String whereClause = DatabaseHelper.COLUMN_QUOTE + " = ?";
        String[] whereArgs = {quote};
        database.delete(DatabaseHelper.TABLE_QUOTES, whereClause, whereArgs);
    }

    public void close() {
        dbHelper.close();
    }
}