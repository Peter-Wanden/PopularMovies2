package com.example.peter.popularmovies2.utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.example.peter.popularmovies2.model.Movie;
import java.util.ArrayList;

/**
 * Created by peter on 20/03/2018.
 * Loads a list of movies by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private final int mMovieSearchType;

    /**
     * Constructs a new {@link MovieLoader} that returns a list of movies.
     *
     * @param context - of the activity or fragment making the request.
     * @param movieSearchType - the type of search requested.
     */
    public MovieLoader(Context context, int movieSearchType) {
        super(context);
        mMovieSearchType = movieSearchType;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is done on a background thread.
     * @return - A list of Movie objects
     */
    @Override
    public ArrayList<Movie> loadInBackground() {

        // Perform the network request, parse the response, and extract a list of Movies.
        return JsonUtils.getMovieData(mMovieSearchType);
    }
}