package com.example.peter.popularmovies2.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.repository.MovieContract.MovieEntry;

public class FindFavorites {

    private static final String TAG = FindFavorites.class.getSimpleName();

    public static boolean isFavorite(Context context, Movie movie) {

        Uri findFavoriteUri = MovieEntry
                .CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(movie.getMovieId()))
                .build();

        Log.e(TAG, "Find favorite URI is: " + findFavoriteUri);

        Cursor cursor = context.getContentResolver().query(
                findFavoriteUri,
                null,
                null,
                null,
                null);


        if ((cursor != null ? cursor.getCount() : 0) == 1) {
            cursor.close();
            return true;
        } else
            Log.e(TAG, "cursor.getCount() returned false!");
        return false;
    }

    public static void addFavorite(Context context, Movie movie) {

        context.getContentResolver().insert(MovieEntry.CONTENT_URI, movie.getContentValues());
    }

    public static void removeFavorite(Context context, Movie movie) {

        Uri remove = MovieEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(movie.getMovieId())).build();

        Log.e(TAG, "Delete URI is: " + remove);

        context.getContentResolver().delete(remove, null, null);
    }
}
