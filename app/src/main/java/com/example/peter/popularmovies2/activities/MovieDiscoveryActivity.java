package com.example.peter.popularmovies2.activities;

import android.content.Intent;
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
 * TODO - 1. Get ALL of the data required for the next phase
 * TODO - 2. Build a DbHelper / Database
 * TODO - 3. Build a ContentProvider
 * TODO - 4. Build a Contract
 * TODO - When everything is running as it should be, and if there is time, implement suggestions.
 */
public class MovieDiscoveryActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        MovieGridViewFragment.OnMovieSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Main view for this activity
        setContentView(R.layout.activity_movie_discovery);
        // Find the nav bar and attach a listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_widget);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        /* Instantiate a movie grid view fragment */
        MovieGridViewFragment popularMovies = new MovieGridViewFragment();
        /* Add it to the main display */
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.movie_discovery_fragment_container_recycler_view, popularMovies)
                .commit();
    }

    /**
     * Called when bottom navigation bar buttons are selected. witches through the options
     *
     * @param item - the id of the icon that has been pressed.
     * @return boolean that returns true when transaction is completed.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Item from the nav bar has been selected so start fragment transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Switch through the menu item ID
        switch (item.getItemId()) {

            // Top rated movies.
            case R.id.movies_highest_rated:
                // Find the view, set the title.
                setTitle(R.string.movies_top_rated);
                // Create a fragment and bind it to the fragment view.
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

            // Most popular movies.
            case R.id.movies_most_popular:
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

            // Todo - implement favorites
            case R.id.movies_favorites:
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
}
