package com.example.peter.popularmovies2.fragments;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager.LoaderCallbacks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.adapters.PosterAdapter;
import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.utils.MovieLoader;
import com.example.peter.popularmovies2.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Created by peter on 21/03/2018.
 * Displays a list of movies in a grid view
 */

public class MovieGridViewFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Movie>>,
        PosterAdapter.PosterAdapterOnClickHandler {

    // Loader id
    private static final int POSTER_LOADER_ID = 100;
    // Instantiate adapter
    protected PosterAdapter mPosterAdapter;
    // Instantiate RecyclerView
    protected RecyclerView mRecyclerView;
    // Instantiate LayoutManager
    protected GridLayoutManager layoutManager;
    // Loading indicator
    private View loadingIndicator;
    // TextView that is displayed when the movie list is empty
    private TextView mEmptyStateTextView;
    // The list of movies
    protected ArrayList<Movie> movies;

    // Mandatory empty constructor for instantiating the fragment
    public MovieGridViewFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View rootView = inflater.inflate(R.layout.fragment_movie_recycler_view, container, false);

        // todo - get the data in the bundle that was sent to this fragment

        // todo - this may not be needed. See comment at {@link onSaveInstanceState}
        if(savedInstanceState !=null) {
            movies = savedInstanceState.getParcelableArrayList("movieList");
            // Update the data source in the adapter.
            mPosterAdapter.updateMovies(movies);
            // Turn off the empty state text view.
            mEmptyStateTextView.setVisibility(View.GONE);
        }

        /* Get a ref to the loading indicator */
        loadingIndicator = getActivity().findViewById(R.id.loading_indicator);

        /* Get a reference to the empty view */
        mEmptyStateTextView = getActivity().findViewById(R.id.empty_view);

        /* Get a reference to the recycler view */
        mRecyclerView = rootView.findViewById(R.id.fragment_movie_recycler_view);

        /* Create a new adapter that takes an empty list of Movie objects */
        mPosterAdapter = new PosterAdapter(getActivity(),this);

        /* GridLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a grid layout.
         */
        layoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.num_columns));
        layoutManager.getHeight();

        /* Connect the layout manager to the RecyclerView */
        mRecyclerView.setLayoutManager(layoutManager);

        /* Developer docs recommend using this performance improvement if all of the views are the
         * same size. They are actually not, as some are text and some are images. The use of
         * setHasFixedSize here is to force the views to be of equal size.
         */
        mRecyclerView.setHasFixedSize(true);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mPosterAdapter);

        /* Check to see if we have a valid network connection */
        if (NetworkUtils.getNetworkStatus(getActivity())) {

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

        /* return the view */
        return rootView;
    }

    @Override
    public void onClick(Movie clickedMovie, int adapterPosition) {
        // Todo - open the movie detail activity from here
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int loaderId, @Nullable Bundle bundle) {
        // ToDo - Pass in the movie search type as the second parameter
        return new MovieLoader(getActivity(), 1);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
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

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        // Clear out the data in the adapter
        mPosterAdapter.updateMovies(null);
    }

    // Todo - I don't think we need this if w are using loaders as they will survive state changes
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putParcelableArrayList("movieList", movies);
    }
}
