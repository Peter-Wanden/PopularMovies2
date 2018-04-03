package com.example.peter.popularmovies2.activities;

import android.content.ContentValues;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.app.Constants;
import com.example.peter.popularmovies2.databinding.ActivityMovieDetailBinding;
import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.repository.MovieContract;
import com.example.peter.popularmovies2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by peter on 20/03/2018.
 * Manages the detail view
 */

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    Movie mSelectedMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets up the view for us to bind data to.
        ActivityMovieDetailBinding detailBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        // Extract the parcelable data from the intent and turn it back into a Movie object
        mSelectedMovie = getIntent().getParcelableExtra(Constants.SELECTED_MOVIE_KEY);

        // Load the backdrop
        if (mSelectedMovie.getBackdropImagePath() != null) {
            URL backdropUrl = NetworkUtils.getMovieImageUrl(Constants
                    .IMAGE_SIZE_XLARGE, mSelectedMovie.getBackdropImagePath());
            if (backdropUrl != null) {
                String backDrop = backdropUrl.toString();
                // Load the backdrop.
                Picasso.with(this)
                        .load(backDrop)
                        .into(detailBinding.movieDetailTrailerThumbnailIv);
            }
        }

        // Load the poster
        if (mSelectedMovie.getPosterImagePath() != null) {
            URL posterUrl = NetworkUtils.getMovieImageUrl(Constants
                    .IMAGE_SIZE_SMALL, mSelectedMovie.getPosterImagePath());
            if (posterUrl != null) {
                String poster = posterUrl.toString();
                // Load the poster.
                Picasso.with(this)
                        .load(poster)
                        .into(detailBinding.movieDetailPosterSmallIv);
            }
        }

        // Set the title
        detailBinding.movieDetailTitleTv.setText(mSelectedMovie.getOriginalTitle());

        // Set the rating
        detailBinding.movieDetailVoteAverageTv.setText(String.valueOf(mSelectedMovie.getUserRating()));

        // Set the year
        String movieReleaseFullDate = mSelectedMovie.getMovieReleaseDate();
        String[] datePart = movieReleaseFullDate.split("-");
        String year = datePart[0];

        detailBinding.movieDetailReleaseYear.setText(year);

        // Set the synopsis
        detailBinding.movieDetailDescriptionTv.setText(mSelectedMovie.getMovieSynopsis());

        // Set a clickListener to the favorites button
        detailBinding.movieDetailFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieToFavorites();
            }
        });

        // Get the movie extra details
        // Get the details URL
        URL movieVideoUrl = NetworkUtils.getMovieVideos(mSelectedMovie.getMovieId());
        assert movieVideoUrl != null;
        Log.e(TAG, "Movie videos URL is: " + movieVideoUrl.toString());

        URL movieReviewsUrl = NetworkUtils.getMovieReviews(mSelectedMovie.getMovieId());
        Log.e(TAG, "Movie reviews URL is: " + movieReviewsUrl.toString());
    }
    private void addMovieToFavorites() {
        ContentValues values = mSelectedMovie.getContentValues();
        Log.e(TAG, "Content Values to save are: " + values);

        Uri uri = getContentResolver()
                .insert(MovieContract.MovieEntry.CONTENT_URI, values);

        Log.e(TAG, "Favorite movie save Uri: " + uri);
    }
}