package com.example.peter.popularmovies2.app;

import com.example.peter.popularmovies2.BuildConfig;

/**
 * Created by peter on 09/03/2018.
 * Contains the applications constant fields
 */

public class Constants {

    /* URL Elements */
    public static final String BASE_SEARCH_URL = "https://api.themoviedb.org/3";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p";
    public static final String API_KEY = "api_key";
    public static final String API_KEY_VALUE = BuildConfig.API_KEY;
    public static final String APPEND_TO_RESPONSE = "append_to_response";
    public static final String EXTRAS = "videos,reviews";
    // Search types
    public static final int MOST_POPULAR = 0;
    public static final int HIGHEST_RATED = 1;
    public static final int FAVORITES = 2;
    public static final String PATH_MOVIE = "movie";
    public static final String POPULARITY = "popular";
    public static final String RATING = "top_rated";

    /* Image elements */
    // Image sizes
    public static final String IMAGE_SIZE_SMALL = "w185";
    public static final String IMAGE_SIZE_MEDIUM = "w342";
    public static final String IMAGE_SIZE_LARGE = "w500";
    public static final String IMAGE_SIZE_XLARGE = "w780";

    /* Strings used as Key - pair's for moving objects about */
    public static final String SELECTED_MOVIE_KEY = "selected_movie";
    public static final String SEARCH_TYPE_KEY = "search_type";
}
