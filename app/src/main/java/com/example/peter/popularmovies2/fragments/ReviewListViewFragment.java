package com.example.peter.popularmovies2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.adapters.ReviewAdapter;
import com.example.peter.popularmovies2.model.Review;
import com.example.peter.popularmovies2.utils.NetworkUtils;
import com.example.peter.popularmovies2.utils.ReviewLoader;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Displays a list of movie reviews
 * Created by peter on 26/03/2018.
 */

public class ReviewListViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Review>> {

    private static final String TAG = ReviewListViewFragment.class.getSimpleName();

    private static final int REVIEW_LOADER_ID = 301;
    protected ReviewAdapter mReviewAdapter;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    private TextView mEmptyStateTextView;
    private View mLoadingIndicator;
    private int mMovieId;


    public ReviewListViewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_review_recycler_view, container,
                false);
        mRecyclerView = rootView.findViewById(R.id.fragment_review_recycler_view);
        mLoadingIndicator = rootView.findViewById(R.id.review_loading_indicator);
        mEmptyStateTextView = rootView.findViewById(R.id.review_empty_view);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mReviewAdapter = new ReviewAdapter(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mReviewAdapter);

        if (savedInstanceState == null) {
            if (NetworkUtils.getNetworkStatus(Objects.requireNonNull(getActivity()))) {
                getLoaderManager().initLoader(REVIEW_LOADER_ID, null, this);
            }
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Review>> onCreateLoader(
            int id,
            @Nullable Bundle args) {

        return new ReviewLoader(getActivity(), mMovieId);
    }

    @Override
    public void onLoadFinished(
            @NonNull Loader<ArrayList<Review>> loader,
            ArrayList<Review> reviews) {

        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.movie_detail_no_reviews);

        if (reviews != null && !reviews.isEmpty()) {
            mReviewAdapter.updateReviews(reviews);
            mEmptyStateTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Review>> loader) {
        mReviewAdapter.updateReviews(null);
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }
}
