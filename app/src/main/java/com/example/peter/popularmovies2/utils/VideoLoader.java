package com.example.peter.popularmovies2.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.peter.popularmovies2.model.Video;

import java.util.ArrayList;

public class VideoLoader extends AsyncTaskLoader<ArrayList<Video>> {

    private final int mMovieId;
    private ArrayList<Video> mVideos;

    public VideoLoader(@NonNull Context context, int movieId) {
        super(context);
        mMovieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (mVideos != null) {
            deliverResult(mVideos);
        } else {
            forceLoad();
        }
        super.onStartLoading();
    }

    @Nullable
    @Override
    public ArrayList<Video> loadInBackground() {
        mVideos = GsonUtils.getVideos(mMovieId);
        return mVideos;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Video> videos) {
        mVideos = videos;
        super.deliverResult(videos);
    }
}
