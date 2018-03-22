package com.example.peter.popularmovies2.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.fragments.MovieGridViewFragment;

public class MovieDiscoveryActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    // Todo - receive the clicked movie object from the fragment.
    // todo - then open it in MovieDetail via an intent

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
        // Item from the nav bar has been selected so start frag transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Switch through the menu item ID
        switch (item.getItemId()) {

            // Top rated movies
            case R.id.movies_rated_top:
                // Find the view, set the title
                setTitle(R.string.movies_top_rated);
                MovieGridViewFragment topRatedFragment = (MovieGridViewFragment)
                        fragmentManager.findFragmentById(R.id.fragment_movie_recycler_view);
                // Check if there is a previous instance available
                if (topRatedFragment == null) topRatedFragment = new MovieGridViewFragment();
                fragmentTransaction.replace(R.id.movie_discovery_fragment_container_recycler_view,
                        topRatedFragment).commit();
                break;

            // Most popular movies - fragment / comments same as case movies_top_rated
            case R.id.movies_most_popular:
                setTitle(R.string.movies_most_popular);
                MovieGridViewFragment mostPopularFragment = (MovieGridViewFragment)
                        fragmentManager.findFragmentById(R.id.fragment_movie_recycler_view);
                if (mostPopularFragment == null) {
                    mostPopularFragment = new MovieGridViewFragment();
                }
                fragmentTransaction.replace(R.id.movie_discovery_fragment_container_recycler_view,
                        mostPopularFragment).commit();
                break;

            // Todo - implement favorites
            case R.id.movies_favorites:
                setTitle(R.string.movies_favorite);
                MovieGridViewFragment favoritesFragment = (MovieGridViewFragment)
                        fragmentManager.findFragmentById(R.id.fragment_movie_recycler_view);
                if (favoritesFragment == null) {
                    favoritesFragment = new MovieGridViewFragment();
                }
                fragmentTransaction.replace(R.id.movie_discovery_fragment_container_recycler_view,
                        favoritesFragment).commit();
                break;
        }
        return true;
    }
}
