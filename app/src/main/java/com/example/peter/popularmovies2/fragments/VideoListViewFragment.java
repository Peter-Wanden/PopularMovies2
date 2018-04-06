package com.example.peter.popularmovies2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.example.peter.popularmovies2.model.Video;

import java.util.ArrayList;

/**
 * Displays a list of movie videos and trailers
 * Created by peter on 26/03/2018.
 * Todo - Implement Video fragment
 */

public class VideoListViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Video>> {

    private static final String TAG = VideoListViewFragment.class.getSimpleName();

    private static final int VIDEO_LOADER_ID = 401;
    protected VideoAdapter;

    @NonNull
    @Override
    public Loader<ArrayList<Video>> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Video>> loader, ArrayList<Video> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Video>> loader) {

    }
}
