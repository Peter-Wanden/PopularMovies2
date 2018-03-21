package com.example.peter.popularmovies2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.adapters.PosterAdapter;
import com.example.peter.popularmovies2.model.Movie;

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

    // Mandatory empty constructor for instantiating the fragment
    public MovieGridViewFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View rootView = inflater.inflate(R.layout.fragment_movie_recycler_view, container, false);

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

        /* return the view */
        return rootView;

    }

    @Override
    public void onClick(Movie clickedMovie, int adapterPosition) {

    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {

    }
}
