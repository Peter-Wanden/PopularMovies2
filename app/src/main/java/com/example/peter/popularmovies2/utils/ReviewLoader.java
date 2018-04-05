package com.example.peter.popularmovies2.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.peter.popularmovies2.model.Review;

import java.util.ArrayList;


public class ReviewLoader extends AsyncTaskLoader<ArrayList<Review>> {

    private final int mMovieId;
    private ArrayList<Review> mReviews;

    public ReviewLoader(Context context, int movieId) {
        super(context);
        mMovieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (mReviews != null) {
            deliverResult(mReviews);
        } else {
            forceLoad();
        }
        super.onStartLoading();
    }

    @Nullable
    @Override
    public ArrayList<Review> loadInBackground() {

        mReviews = GsonUtils.getMovieReviews(mMovieId);
        return mReviews;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Review> reviews) {

        mReviews = reviews;
        super.deliverResult(reviews);
    }
}
