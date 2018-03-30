package com.example.peter.popularmovies2.repository;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static final String PATH_MOVIE = "movie";
    private static final String CONTENT_AUTHORITY = "com.example.peter.popularmovies2";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private MovieContract() {
    }

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIE);

        /* The MIME type of the {@link #CONTENT_URI} for a list of movies */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        /* The MIME type of the {@link #CONTENT_URI} for a single movie */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/ " + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public final static String TABLE_NAME = "movies";

        public static final String _ID = BaseColumns._ID;

        /* Database columns */
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "movie_title";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_YEAR = "release_year";
    }
}
