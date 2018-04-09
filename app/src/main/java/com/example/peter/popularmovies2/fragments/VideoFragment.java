package com.example.peter.popularmovies2.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.adapters.VideoAdapter;
import com.example.peter.popularmovies2.app.Constants;
import com.example.peter.popularmovies2.model.Video;
import com.example.peter.popularmovies2.utils.NetworkUtils;
import com.example.peter.popularmovies2.utils.VideoLoader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Displays a list of a movies videos and trailers available on YouTube
 * Created by peter on 26/03/2018.
 */

public class VideoFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Video>>,
        VideoAdapter.VideoAdapterOnClickHandler {

    private static final int VIDEO_LOADER_ID = 401;
    private VideoAdapter mVideoAdapter;
    private RecyclerView mRecyclerView;
    private View mLoadingIndicator;
    private TextView mEmptyStateTextView;
    private int mMovieId;

    public VideoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video_recycler_view, container,
                false);
        mLoadingIndicator = rootView.findViewById(R.id.video_loading_indicator);
        mEmptyStateTextView = rootView.findViewById(R.id.video_empty_view);
        mRecyclerView = rootView.findViewById(R.id.fragment_video_recycler_view);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mVideoAdapter = new VideoAdapter(getActivity(), this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mVideoAdapter);

        if (savedInstanceState == null) {
            if (NetworkUtils.getNetworkStatus(Objects.requireNonNull(getActivity()))) {
                getLoaderManager().initLoader(VIDEO_LOADER_ID, null, this);
            }
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Video>> onCreateLoader(int id, @Nullable Bundle videos) {
        return new VideoLoader(Objects.requireNonNull(getActivity()), mMovieId);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Video>> loader, ArrayList<Video> videos) {
        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_videos_available);

        if (videos != null && !videos.isEmpty()) {
            mVideoAdapter.updateVideos(videos);
            mEmptyStateTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Video>> loader) {
        mVideoAdapter.updateVideos(null);
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    /* Implements the intents for the click interface in the VideoAdapter */
    @Override
    public void onClick(Video currentVideo, int intentType) {

        switch (intentType){

            // Launch a video either in the YouTube app or in a browser
            case Constants.LAUNCH_VIDEO:

                Intent youTubeAppIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("vnd.youtube:" + currentVideo.getVideoKey()));

                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constants.YT_VIDEO_URL + currentVideo.getVideoKey()));

                try {
                    Objects.requireNonNull(getActivity()).startActivity(youTubeAppIntent);
                } catch (ActivityNotFoundException appNotFoundLaunchBrowser) {
                    getActivity().startActivity(browserIntent);
                }
                break;

            // Create a simple share intent to share the video URL
            case Constants.SHARE_VIDEO:

                // Define the variables
                String mimeType = "text/plain";
                String title = "Share this video";
                String urlToShare = Uri.parse(Constants.YT_VIDEO_URL + currentVideo.getVideoKey())
                        .toString();

                ShareCompat
                        .IntentBuilder
                        .from(Objects.requireNonNull(getActivity()))
                        .setType(mimeType)
                        .setChooserTitle(title)
                        .setText(urlToShare)
                        .startChooser();

                break;
        }
    }
}
