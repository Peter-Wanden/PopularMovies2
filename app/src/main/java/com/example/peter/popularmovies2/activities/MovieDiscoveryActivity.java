package com.example.peter.popularmovies2.activities;

import android.support.v4.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.peter.popularmovies2.adapters.PosterAdapter;
import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.app.Constants;
import com.example.peter.popularmovies2.fragments.MovieGridViewFragment;
import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.utils.MovieLoader;
import com.example.peter.popularmovies2.utils.NetworkUtils;

import java.util.ArrayList;

public class MovieDiscoveryActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<Movie>>,
        PosterAdapter.PosterAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {

    // Todo -

    // Loader id
    private static final int POSTER_LOADER_ID = 100;

    // TextView that is displayed when the movie list is empty
    private TextView mEmptyStateTextView;

    // Loading indicator
    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_discovery);

        /* Instantiate a movie view fragment */
        MovieGridViewFragment popularMovies = new MovieGridViewFragment();
        /* Add it to the main display */
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.movie_discovery_fragment_container_recycler_view, popularMovies)
                .commit();

        /* Get a ref to the loading indicator */
        loadingIndicator = findViewById(R.id.loading_indicator);

        /* Get a reference to the empty view */
        mEmptyStateTextView = findViewById(R.id.empty_view);

        /* Get a reference to shared preferences */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        /* Register a listener so we are notified of any changes */
        prefs.registerOnSharedPreferenceChangeListener(this);

        /* Check to see if we have a valid network connection */
        if (NetworkUtils.getNetworkStatus(this)) {

            /* Ensures a loader is initialized and active. If the loader doesn't already
             * exist, one is created and (if the activity/fragment is currently started)
             * starts the loader. Otherwise the last created loader is re-used.
             */
            getLoaderManager().initLoader(POSTER_LOADER_ID, null, this);

        } else {
            // Turn the loading indicator off
            loadingIndicator.setVisibility(View.GONE);
            // Update the empty state text view with network connection error message
            mEmptyStateTextView.setText(R.string.movie_discovery_no_network);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(getString(R.string.settings_order_by_key))) {

            // Restart the loader to re-query the server as the query settings have been updated
            getLoaderManager().restartLoader(POSTER_LOADER_ID, null, this);
        }
    }

    /**
     * Instantiates and returns a new loader for the given loaderId
     *
     * @param loaderId - A unique integer that identifies the loader.
     * @param bundle   - additional data.
     * @return - to onLoadFinished an ArrayList of Movie objects.
     */
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int loaderId, Bundle bundle) {

        // Get the user preference for the move search type
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int orderBy = Integer.parseInt(preferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        ));

        // On a background thread return an ArrayList of movies
        return new MovieLoader(this, orderBy);
    }

    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loaderId - The loader id. In this case POSTER_LOADER_ID
     * @param movies   - A list of Movie objects returned from the onCreateLoader method.
     */
    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loaderId, ArrayList<Movie> movies) {

        // Hide the loading indicator as data has been loaded.
        loadingIndicator.setVisibility(View.GONE);

        // Assume no data.
        mEmptyStateTextView.setText(R.string.movie_discovery_no_movies);

        // However if there is data.
        if (movies != null && !movies.isEmpty()) {
            // Update the data source in the adapter.
            mPosterAdapter.updateMovies(movies);
            // Turn off the empty state text view.
            mEmptyStateTextView.setVisibility(View.GONE);
        }
    }

    /**
     * Called when a previously created loader is being reset, thus making its data unavailable
     * @param loader - The ID of the loader to reset.
     */
    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        // Clear out the data in the adapter
        mPosterAdapter.updateMovies(null);
    }

    /**
     * This method is for responding to clicks from our list.
     *
     * @param selectedMovie   is a complete Movie object containing all of the selected movies data.
     * @param adapterPosition is the position of the adapter.
     */
    @Override
    public void onClick(Movie selectedMovie, int adapterPosition) {

        // Intent to open MovieDetailActivity
        Intent movieDetailIntent = new Intent(MovieDiscoveryActivity.this, MovieDetailActivity.class);
        movieDetailIntent.putExtra(Constants.SELECTED_MOVIE_KEY, selectedMovie);
        startActivity(movieDetailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.movies_rated_top) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
