package com.example.peter.popularmovies2.utils;

import android.content.ContentValues;

import com.example.peter.popularmovies2.model.Movie;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by peter on 20/03/2018.
 * Utility class with methods to return a new list of movie objects.
 */

public class GsonUtils {

    /**
     * Query the TMDb server and return a list of Movie objects based on user preferences.
     */
    static ArrayList<Movie> getMovieData(int searchType) {

        /* Create the URL object */
        URL url = NetworkUtils.getMovieSearchUrl(searchType);

        // Perform a HTTP request to the URL and receive a JSON response back.
        String jsonResponse = null;
        try {
            assert url != null;
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the list of {@link Movie} objects.
        NewMovieResults movieResults = new Gson().fromJson(jsonResponse, NewMovieResults.class);

        return movieResults.results;
    }

    // Inner class provides a container for the movie results
    public class NewMovieResults {
        ArrayList<Movie> results;
    }
}