package com.example.peter.popularmovies2.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.example.peter.popularmovies2.activities.MovieDiscoveryActivity;
import com.example.peter.popularmovies2.app.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by peter on 20/03/2018.
 * This class constructs a URL and then queries 'The Movie Database' API.
 * Each URL returns a list of either the most popular or highest rated movies with extras if
 * requested.
 */

public class NetworkUtils {

    /* Log tag for this class */
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link NetworkUtils}
     * object. This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name tMDBApiUtils (an object instance of QueryUtils is not needed).
     */
    private NetworkUtils() {
    }

    /**
     * Checks for network connectivity.
     *
     * @param context - Context of the class requesting connectivity.
     * @return - true for access.
     */
    public static boolean getNetworkStatus(Context context) {

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(MovieDiscoveryActivity.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        // If there is an active network return true, else return false
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * This method constructs and returns a URL that will search by 'POPULARITY' or 'RATING'
     * depending on the args sent to it.
     *
     * @param searchType - Defines the type of search URL to build.
     * @return - A URL converted to a String.
     */
    public static URL getMovieSearchUrl(int searchType) {

        Uri.Builder searchUri = Uri.parse(Constants.BASE_SEARCH_URL).buildUpon()
                .appendPath(Constants.PATH_MOVIE);

        switch (searchType) {
            case Constants.MOST_POPULAR:
                searchUri.appendPath(Constants.POPULARITY);
                break;

            case Constants.HIGHEST_RATED:
                searchUri.appendPath(Constants.RATING);
                break;

            default:
                // An incorrect argument should not crash the app. Instead return most popular
                searchUri.appendPath(Constants.POPULARITY);
                // Log the error
                Log.e(LOG_TAG, "Search criteria not supported for search type: "
                        + searchType);
        }

        // Append the API key, and return the completed search URL as a String.
        searchUri.appendQueryParameter(Constants.API_KEY, Constants.API_KEY_VALUE).build();

        try {
            return new URL(searchUri.toString());

            // return new URL(searchUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Constructs and returns a URL that will return a specified image from the server
     *
     * @param imageType - See {@link Constants} - the size of image
     * @param imagePath - The path to the image for the selected movie.
     * @return          - A complete Url that returns an image of the correct type and size.
     */
    public static URL getMovieImageUrl(String imageType, String imagePath) {

        // Build the base Uri
        Uri.Builder getImageUrl = Uri.parse(Constants.BASE_IMAGE_URL).buildUpon();

        // Add the required image size
        switch (imageType) {
            case Constants.IMAGE_SIZE_SMALL:
                getImageUrl.appendPath(Constants.IMAGE_SIZE_SMALL);
                break;
            case Constants.IMAGE_SIZE_MEDIUM:
                getImageUrl.appendPath(Constants.IMAGE_SIZE_MEDIUM);
                break;
            case Constants.IMAGE_SIZE_LARGE:
                getImageUrl.appendPath(Constants.IMAGE_SIZE_LARGE);
                break;
            case Constants.IMAGE_SIZE_XLARGE:
                getImageUrl.appendPath(Constants.IMAGE_SIZE_XLARGE);
                break;
            default:
                Log.i(LOG_TAG, "Image size not supported");
        }

        // Add the image path
        getImageUrl.appendEncodedPath(imagePath);

        // Return the Url
        try {
            return new URL(getImageUrl.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns information relating to any movie trailers.
     * @param movieId - the Id of the selected movie.
     * @return - A URL that queries the tMDB database.
     */

    public static URL getMovieVideos (int movieId){

        Uri.Builder getAppendedUrl = Uri.parse(Constants.BASE_SEARCH_URL).buildUpon();
        getAppendedUrl.appendPath(Constants.PATH_MOVIE);
        getAppendedUrl.appendEncodedPath(String.valueOf(movieId));
        getAppendedUrl.appendPath(Constants.PATH_VIDEO);
        getAppendedUrl.appendQueryParameter(Constants.API_KEY, Constants.API_KEY_VALUE);

        try {
            return new URL(getAppendedUrl.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns information relating to any movie reviews.
     * @param movieId - the Id of the selected movie.
     * @return - A URL that queries the tMDB database.
     */

    public static URL getMovieReviews (int movieId){

        Uri.Builder getAppendedUrl = Uri.parse(Constants.BASE_SEARCH_URL).buildUpon();
        getAppendedUrl.appendPath(Constants.PATH_MOVIE);
        getAppendedUrl.appendEncodedPath(String.valueOf(movieId));
        getAppendedUrl.appendPath(Constants.PATH_REVIEW);
        getAppendedUrl.appendQueryParameter(Constants.API_KEY, Constants.API_KEY_VALUE);

        try {
            return new URL(getAppendedUrl.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns a URL pointing to a thumbnail image representing the trailer vodeo
     *
     * @param trailerId - The YouTube video ID
     * @return - A URL that points to a thumbnail image
     */
    public static URL getYouTubeThumbnailUrl(String trailerId) {
        Uri.Builder getYouTubeThumbnail = Uri.parse(Constants.YOU_TUBE_BASE_THUMBNAIL_IMAGE_URL)
                .buildUpon();
        getYouTubeThumbnail.appendPath(trailerId);
        getYouTubeThumbnail.appendPath(Constants.YOU_TUBE_THUMBNAIL_URL_END_POINT);

        try {
            return new URL(getYouTubeThumbnail.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
}
