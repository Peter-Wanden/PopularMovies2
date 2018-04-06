package com.example.peter.popularmovies2.utils;

import android.util.Log;

import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.model.Review;
import com.example.peter.popularmovies2.model.Video;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by peter on 20/03/2018.
 * Utility class with methods to return a new list of movie objects.
 */

public class GsonUtils {

    private static final String TAG = GsonUtils.class.getSimpleName();

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

    /**
     * Query the TMDb server and return a list of Review objects for a movie.
     */
    static ArrayList<Review> getMovieReviews(int movieId) {

        /* Create the URL object */
        URL url = NetworkUtils.getMovieReviews(movieId);

        // Perform a HTTP request to the URL and receive a JSON response back.
        String jsonResponse = null;
        try {
            assert url != null;
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the results
        NewReviewResults reviewResults = new Gson().fromJson(jsonResponse, NewReviewResults.class);

        return reviewResults.results;
    }

    public class NewReviewResults {
        ArrayList<Review> results;
    }


    static ArrayList<Video> getVideos(int movieId) {

        /* Create the URL object */
        URL url = NetworkUtils.getMovieVideos(movieId);

        // Perform a HTTP request to the URL and receive a JSON response back.
        String jsonResponse = null;
        try {
            assert url != null;
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the results
        NewVideoResults videoResults = new Gson().fromJson(jsonResponse, NewVideoResults.class);

        return videoResults.results;
    }

    public class NewVideoResults {
        ArrayList<Video> results;
    }
}