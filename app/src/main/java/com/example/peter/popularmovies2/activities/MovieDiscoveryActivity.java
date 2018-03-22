package com.example.peter.popularmovies2.activities;



import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.fragments.MovieGridViewFragment;


public class MovieDiscoveryActivity extends AppCompatActivity  {

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
    }
}
