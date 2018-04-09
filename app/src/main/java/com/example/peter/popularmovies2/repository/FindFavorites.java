package com.example.peter.popularmovies2.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.repository.MovieContract.MovieEntry;

/**
 * Finds if a movie is in the favorites database
 */
public class FindFavorites {

    public static boolean isFavorite(Context context, Movie movie) {

        Uri findFavoriteUri = MovieEntry
                .CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(movie.getMovieId()))
                .build();

        Cursor cursor = context.getContentResolver().query(
                findFavoriteUri,
                null,
                null,
                null,
                null);


        if ((cursor != null ? cursor.getCount() : 0) == 1) {
            cursor.close();
            return true;
        }
        return false;
    }

    public static void addFavorite(Context context, Movie movie) {

        context.getContentResolver().insert(MovieEntry.CONTENT_URI, movie.getContentValues());
    }

    public static void removeFavorite(Context context, Movie movie) {

        Uri remove = MovieEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(movie.getMovieId())).build();

        context.getContentResolver().delete(remove, null, null);
    }
}
