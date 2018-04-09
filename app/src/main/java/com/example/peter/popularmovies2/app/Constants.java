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
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_VIDEO = "videos";
    public static final String PATH_REVIEW = "reviews";
    public static final String API_KEY = "api_key";
    public static final String API_KEY_VALUE = BuildConfig.API_KEY;
    public static final String YT_BASE_THUMBNAIL_IMAGE_URL = "https://img.youtube.com/vi";
    public static final String YT_VIDEO_URL = "http://www.youtube.com/watch?v=";

    // Search types
    public static final int MOST_POPULAR = 0;
    public static final int HIGHEST_RATED = 1;
    public static final String POPULARITY = "popular";
    public static final String RATING = "top_rated";
    public static final String YT_THUMBNAIL_URL_END_POINT = "0.jpg";

    // Intent Types
    public static final int LAUNCH_VIDEO = 0;
    public static final int SHARE_VIDEO = 1;

    /* Image elements */
    // Image sizes
    public static final String IMAGE_SIZE_SMALL = "w185";
    public static final String IMAGE_SIZE_MEDIUM = "w342";
    public static final String IMAGE_SIZE_LARGE = "w500";
    public static final String IMAGE_SIZE_XLARGE = "w780";

    /* Strings used as Key - pair's for moving objects about */
    public static final String SELECTED_MOVIE_KEY = "selected_movie";
}
