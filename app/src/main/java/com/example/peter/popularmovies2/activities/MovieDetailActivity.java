package com.example.peter.popularmovies2.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.app.Constants;
import com.example.peter.popularmovies2.databinding.ActivityMovieDetailBinding;
import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.repository.MovieContract;
import com.example.peter.popularmovies2.utils.MovieLoader;
import com.example.peter.popularmovies2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by peter on 20/03/2018.
 * Manages the detail view
 */

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    Movie selectedMovie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets up the view for us to bind data to.
        ActivityMovieDetailBinding detailBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        // Extract the parcelable data from the intent and turn it back into a Movie object
        selectedMovie = getIntent().getParcelableExtra(Constants.SELECTED_MOVIE_KEY);

        // Load the backdrop
        if (selectedMovie.getBackdropImagePath() != null) {
            URL backdropUrl = NetworkUtils.getMovieImageUrl(Constants
                    .IMAGE_SIZE_XLARGE, selectedMovie.getBackdropImagePath());
            if (backdropUrl != null) {
                String backDrop = backdropUrl.toString();
                // Load the backdrop.
                Picasso.with(this)
                        .load(backDrop)
                        .into(detailBinding.movieDetailTrailerThumbnailIv);
            }
        }

        // Load the poster
        if (selectedMovie.getPosterImagePath() != null) {
            URL posterUrl = NetworkUtils.getMovieImageUrl(Constants
                    .IMAGE_SIZE_SMALL, selectedMovie.getPosterImagePath());
            if (posterUrl != null) {
                String poster = posterUrl.toString();
                // Load the poster.
                Picasso.with(this)
                        .load(poster)
                        .into(detailBinding.movieDetailPosterSmallIv);
            }
        }

        // Set the title
        detailBinding.movieDetailTitleTv.setText(selectedMovie.getOriginalTitle());

        // Set the rating
        detailBinding.movieDetailVoteAverageTv.setText(String.valueOf(selectedMovie.getUserRating()));

        // Set the year
        String movieReleaseFullDate = selectedMovie.getMovieReleaseDate();
        String[] datePart = movieReleaseFullDate.split("-");
        String year = datePart[0];

        detailBinding.movieDetailReleaseYear.setText(year);

        // Set the synopsis
        detailBinding.movieDetailDescriptionTv.setText(selectedMovie.getMovieSynopsis());

        // Set a clickListener to the favorites button
        detailBinding.movieDetailFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieToFavorites();
            }
        });

        // Get the movie extra details
        // Get the details URL
        URL movieVideoUrl = NetworkUtils.getMovieVideos(selectedMovie.getMovieId());
        assert movieVideoUrl != null;
        Log.e(TAG, "Movie videos URL is: " + movieVideoUrl.toString());

        URL movieReviewsUrl = NetworkUtils.getMovieReviews(selectedMovie.getMovieId());
        Log.e(TAG, "Movie reviews URL is: " + movieReviewsUrl.toString());
    }
    private void addMovieToFavorites() {
        getContentResolver()
                .insert(MovieContract.MovieEntry.CONTENT_URI, selectedMovie.getContentValues());
    }
}