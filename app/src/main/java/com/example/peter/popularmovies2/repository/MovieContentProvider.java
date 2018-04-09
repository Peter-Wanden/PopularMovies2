package com.example.peter.popularmovies2.repository;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.peter.popularmovies2.repository.MovieContract.MovieEntry;

import java.util.Objects;

/**
 * Content Provider
 */
public class MovieContentProvider extends ContentProvider {

    private static final String TAG = MovieContentProvider.class.getSimpleName();

    /* URI matcher for the content URI for the movies table */
    private static final int MOVIES = 100;

    /* URI matcher for the content URI for a single movie */
    private static final int MOVIE_ID = 101;

    /* UriMatcher object to match a content URI to a corresponding code. */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        /* Code to return multiple rows */
        sUriMatcher.addURI(
                MovieContract.CONTENT_AUTHORITY,
                MovieContract.PATH_MOVIE, MOVIES);

        /* Code to return a single row */
        sUriMatcher.addURI(
                MovieContract.CONTENT_AUTHORITY,
                MovieContract.PATH_MOVIE + "/#", MOVIE_ID);
    }

    /* Helper object */
    private MovieDbHelper mMovieDbHelper;

    @Override
    public boolean onCreate() {
        mMovieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = mMovieDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                cursor = database.query(
                        MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case MOVIE_ID:
                selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(
                        MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(
                Objects.requireNonNull(
                        getContext()).
                        getContentResolver(),
                uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues values) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return insertMovie(uri, Objects.requireNonNull(values));
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertMovie(Uri uri, ContentValues contentValues) {

        SQLiteDatabase database = mMovieDbHelper.getWritableDatabase();

        /* Check the validity of the required values provided */
        Integer movieId = contentValues.getAsInteger(
                MovieEntry.COLUMN_MOVIE_ID);

        if (movieId == null) {
            throw new IllegalArgumentException("Movie requires an ID");
        }

        String movieTitle = contentValues.getAsString(
                MovieEntry.COLUMN_TITLE);

        if (movieTitle == null) {
            throw new IllegalArgumentException("Movie requires a title");
        }

        String movieOriginalTitle = contentValues.getAsString(
                MovieEntry.COLUMN_ORIGINAL_TITLE);

        if (movieOriginalTitle == null) {
            throw new IllegalArgumentException("Movie requires an original title");
        }

        long id = database.insert(MovieEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e(TAG, "Failed to inset row for " + uri);
            return null;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mMovieDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                rowsDeleted = database.delete(
                        MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case MOVIE_ID:
                selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                rowsDeleted = database.delete(
                        MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            Objects.requireNonNull(
                    getContext()).
                    getContentResolver().
                    notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return updateMovie(
                        uri,
                        Objects.requireNonNull(values),
                        selection,
                        selectionArgs);

            case MOVIE_ID:

                selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                return updateMovie(
                        uri,
                        Objects.requireNonNull(values),
                        selection,
                        selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateMovie (
            Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {

        /* Check for the minimum required values */
        if (values.containsKey(MovieEntry.COLUMN_MOVIE_ID)) {
            Integer movieId = values.getAsInteger(MovieEntry.COLUMN_MOVIE_ID);

            if (movieId == null) {
                throw new IllegalArgumentException("Movie requires an ID");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_TITLE)) {
            String movieTitle = values.getAsString(MovieEntry.COLUMN_TITLE);

            if (movieTitle == null) {
                throw new IllegalArgumentException("Movie requires a title");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_ORIGINAL_TITLE)) {
            String movieOriginalTitle = values.getAsString(MovieEntry.COLUMN_ORIGINAL_TITLE);

            if (movieOriginalTitle == null) {
                throw new IllegalArgumentException("Movie requires a title");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mMovieDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(
                MovieEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (rowsUpdated != 0) {
            Objects.requireNonNull(
                    getContext()).
                    getContentResolver().
                    notifyChange(uri, null);
        }

        return rowsUpdated;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return MovieEntry.CONTENT_LIST_TYPE;

            case MOVIE_ID:
                return MovieEntry.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
