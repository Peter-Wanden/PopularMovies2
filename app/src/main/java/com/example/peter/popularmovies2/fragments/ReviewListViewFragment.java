package com.example.peter.popularmovies2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.example.peter.popularmovies2.model.Review;

import java.util.ArrayList;

/**
 * Displays a list of movie reviews
 * Created by peter on 26/03/2018.
 * TODO - Implement Review fragment
 */

public class ReviewListViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Review>> {

    private static final String TAG = ReviewListViewFragment.class.getSimpleName();

    private static final int REVIEW_LOADER_ID = 301;


    @NonNull
    @Override
    public Loader<ArrayList<Review>> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Review>> loader, ArrayList<Review> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Review>> loader) {

    }
}
