package com.example.peter.popularmovies2.activities;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.app.Constants;
import com.example.peter.popularmovies2.fragments.FavoritesFragment;
import com.example.peter.popularmovies2.fragments.MovieGridViewFragment;
import com.example.peter.popularmovies2.model.Movie;

/**
 * TODO - When everything is running, implement suggestions.
 * Manages the TopRated, Popular and Movie fragments.
 */
public class MovieDiscoveryActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        MovieGridViewFragment.OnMovieSelectedListener,
        FavoritesFragment.OnFavoriteSelectedListener {

    private static final String TAG = MovieDiscoveryActivity.class.getSimpleName();

    /* Key value pair used for storing navigation state */
    private static final String BOTTOM_NAVIGATION_SELECTED = "navigation_selection";
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Main view for this activity */
        setContentView(R.layout.activity_movie_discovery);

        /* Find the nav bar and attach a listener */
        mNavigationView = findViewById(R.id.bottom_navigation_widget);
        mNavigationView.setOnNavigationItemSelectedListener(this);

        /* If there is no saved instance state, this is a new run, so get the last used navigation
         * selection and load new data.
         */
        if (savedInstanceState == null) {

            int navigationItem = PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .getInt(BOTTOM_NAVIGATION_SELECTED, R.id.movies_highest_rated);

            mNavigationView.setSelectedItemId(navigationItem);
        }
    }

    /**
     * Called when bottom navigation bar buttons are selected, then switches through the options
     *
     * @param item - the id of the icon that has been pressed.
     * @return boolean that returns true when transaction is completed.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        /* An item from the nav bar has been selected, so start fragment transaction */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Switch through the menu item ID */
        switch (item.getItemId()) {

            /* Top rated movies. */
            case R.id.movies_highest_rated:
                // Set the title, find the view.
                setTitle(R.string.movies_top_rated);

                // Binds the new fragment to its view.
                MovieGridViewFragment topRatedFragment = (MovieGridViewFragment)
                        fragmentManager.findFragmentById(R.id.fragment_movie_recycler_view);

                // If the fragment does not exist yet create a new one.
                if (topRatedFragment == null) {
                    topRatedFragment = new MovieGridViewFragment();
                }

                // Replace whatever is in the fragment container with our new fragment.
                fragmentTransaction.replace(R.id.movie_discovery_fragment_container_recycler_view,
                        topRatedFragment).commit();

                // Set the search type within the new fragment.
                topRatedFragment.setMovieSearchType(Constants.HIGHEST_RATED);
                break;

            /* Most popular movies. */
            case R.id.movies_most_popular:
                // Set the title, find the view.
                setTitle(R.string.movies_most_popular);

                MovieGridViewFragment mostPopularFragment = (MovieGridViewFragment)
                        fragmentManager.findFragmentById(R.id.fragment_movie_recycler_view);
                if (mostPopularFragment == null) {
                    mostPopularFragment = new MovieGridViewFragment();
                }
                fragmentTransaction.replace(R.id.movie_discovery_fragment_container_recycler_view,
                        mostPopularFragment).commit();
                mostPopularFragment.setMovieSearchType(Constants.MOST_POPULAR);
                break;

            /* Favorite movies. */
            case R.id.movies_favorites:
                // Set the title, find the view.
                setTitle(R.string.movies_favorite);
                FavoritesFragment favoritesFragment = (FavoritesFragment)
                        fragmentManager.findFragmentById(R.id.fragment_movie_recycler_view);
                if (favoritesFragment == null) {
                    favoritesFragment = new FavoritesFragment();
                }
                fragmentTransaction.replace(R.id.movie_discovery_fragment_container_recycler_view,
                        favoritesFragment).commit();
                break;
        }
        return true;
    }

    /**
     * Interface that receives a movie object from a MovieGridViewFragment instance.
     * The object is passed through two interfaces from the adapter to this activity.
     *
     * @param movie - The movie selected by the user.
     */
    @Override
    public void onMovieSelected(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        // Movie class implements Parcelable so we can add it to a bundle without any extra work.
        intent.putExtra(Constants.SELECTED_MOVIE_KEY, movie);
        startActivity(intent);
    }

    /**
     * Interface that receives a movie object from a FavoritesFragment instance.
     * @param movie - the selected movie.
     */
    @Override
    public void onFavoriteSelected(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        // Movie class implements Parcelable so we can add it to a bundle without any extra work.
        intent.putExtra(Constants.SELECTED_MOVIE_KEY, movie);
        startActivity(intent);
    }

    /* Save the users last selected navigation menu item */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putInt(BOTTOM_NAVIGATION_SELECTED,
                        mNavigationView
                                .getSelectedItemId())
                .apply();
        super.onSaveInstanceState(outState);
    }
}
