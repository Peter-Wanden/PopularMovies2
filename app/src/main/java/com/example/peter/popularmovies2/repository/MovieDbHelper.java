package com.example.peter.popularmovies2.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.peter.popularmovies2.repository.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS "
            + MovieEntry.TABLE_NAME + " ("
            + MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, "
            + MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
            + MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, "
            + MovieEntry.COLUMN_POSTER_PATH + " TEXT, "
            + MovieEntry.COLUMN_BACKDROP_PATH + " TEXT, "
            + MovieEntry.COLUMN_OVERVIEW + " TEXT, "
            + MovieEntry.COLUMN_RATING + " INTEGER, "
            + MovieEntry.COLUMN_RELEASE_YEAR + " TEXT);";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
