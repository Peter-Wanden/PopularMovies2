package com.example.peter.popularmovies2.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.adapters.FavoritesAdapter;
import com.example.peter.popularmovies2.repository.MovieContract.MovieEntry;

import java.util.Objects;

/**
 * Retrieves and displays a list of favorite movies stored in a local database
 */
public class FavoritesFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        FavoritesAdapter.FavoritesAdapterOnClickHandler{

    private static final int FAVORITES_LOADER_ID = 301;
    protected FavoritesAdapter mFavoritesAdapter;
    protected RecyclerView mRecyclerView;
    protected GridLayoutManager mLayoutManager;
    private View mLoadingIndicator;
    private TextView mEmptyStateTextView;
    private int mPosition = RecyclerView.NO_POSITION;

    public FavoritesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_recycler_view, container,
                false);

        mLoadingIndicator = rootView.findViewById(R.id.loading_indicator);
        mEmptyStateTextView = rootView.findViewById(R.id.empty_view);
        mRecyclerView = rootView.findViewById(R.id.fragment_movie_recycler_view);

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

        Uri favoritesUri = MovieEntry.CONTENT_URI;

        String[] projection = {
                MovieEntry._ID,
                MovieEntry.COLUMN_MOVIE_ID,
                MovieEntry.COLUMN_TITLE,
                MovieEntry.COLUMN_POSTER_PATH};

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

    @Override
    public void onClick(int movieId) {
        // ToDo - If movie is in favorites, remove it, else add it
    }
}
