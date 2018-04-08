package com.example.peter.popularmovies2.fragments;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.CursorLoader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.adapters.FavoritesAdapter;
import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.repository.MovieContract.MovieEntry;

import java.util.Objects;

/**
 * Retrieves and displays a list of favorite movies stored in a local database
 */
public class FavoritesFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        FavoritesAdapter.FavoritesAdapterOnClickHandler{

    private static final String TAG = FavoritesFragment.class.getSimpleName();

    // Loader id
    private static final int FAVORITES_LOADER_ID = 301;
    // Instantiate adapter
    private FavoritesAdapter mFavoritesAdapter;
    // Interface to activity
    private OnFavoriteSelectedListener mMovieCallback;
    // Instantiate LayoutManager
    private GridLayoutManager mLayoutManager;
    // Parcelable for mLayoutManager's list state
    private Parcelable mListState;
    // Parcelable key for list mListState
    private static final String LIST_STATE_KEY = "list_state_key";
    // Loading indicator
    private View mLoadingIndicator;
    // TextView that is displayed when the movie list is empty
    private TextView mEmptyStateTextView;
    // RecyclerView position
    private int mPosition = RecyclerView.NO_POSITION;

    // Mandatory empty constructor for instantiating the fragment
    public FavoritesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.e(TAG, "onCreateView called");

        View rootView = inflater.inflate(R.layout.fragment_movie_recycler_view, container,
                false);

        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.fragment_movie_recycler_view);

        mFavoritesAdapter = new FavoritesAdapter(getActivity(), this);

        mLayoutManager = new GridLayoutManager(
                getActivity(),
                getResources().getInteger(R.integer.num_columns));
        mLayoutManager.getHeight();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFavoritesAdapter);

        getLoaderManager().initLoader(FAVORITES_LOADER_ID, null, this);

        return rootView;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        Log.e(TAG, "onCreateLoader called");

        Uri favoritesUri = MovieEntry.CONTENT_URI;

        String[] projection = {
                MovieEntry._ID,
                MovieEntry.COLUMN_MOVIE_ID,
                MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_ORIGINAL_TITLE,
                MovieEntry.COLUMN_POSTER_PATH,
                MovieEntry.COLUMN_BACKDROP_PATH,
                MovieEntry.COLUMN_OVERVIEW,
                MovieEntry.COLUMN_RATING,
                MovieEntry.COLUMN_RELEASE_YEAR};

        String sortOrder = MovieEntry.COLUMN_TITLE + " ASC";

        return new CursorLoader(
                Objects.requireNonNull(getActivity()),
                favoritesUri,
                projection,
                null,
                null, sortOrder);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        Log.e(TAG, "onLoadFinished called");

        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.empty_state_text_view_favorites);

        if(mPosition == RecyclerView.NO_POSITION) mPosition = 0;

        if(data.getCount() != 0) {
            mEmptyStateTextView.setVisibility(View.GONE);
            mFavoritesAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mFavoritesAdapter.swapCursor(null);
    }

    // OnMovieSelected interface, calls a method in the host activity named onMovieSelected
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.e(TAG, "onAttach called");

        // Make sure the host activity has implemented the OnMovieSelectedListener callback interface
        try {
            mMovieCallback = (OnFavoriteSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMovieSelectedListener");
        }
    }

    // OnMovieSelected interface, calls a method in the host activity named onFavoriteSelected
    public interface OnFavoriteSelectedListener {
        void onFavoriteSelected(Movie movie);
    }

    /* Click interface from the FavouritesAdapter */
    @Override
    public void onClick(Movie clickedMovie) {
        mMovieCallback.onFavoriteSelected(clickedMovie);
    }

    /* Save LayoutManager's state */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the list state
        mListState = mLayoutManager.onSaveInstanceState();
        Log.e(TAG, "Out state saved: " + mListState);
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }
}