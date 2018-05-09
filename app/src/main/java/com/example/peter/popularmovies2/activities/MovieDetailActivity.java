package com.example.peter.popularmovies2.activities;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.app.Constants;
import com.example.peter.popularmovies2.databinding.ActivityMovieDetailBinding;
import com.example.peter.popularmovies2.fragments.ReviewFragment;
import com.example.peter.popularmovies2.fragments.VideoFragment;
import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.repository.FindFavorites;
import com.example.peter.popularmovies2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;
import java.net.URL;

/**
 * Created by peter on 20/03/2018.
 * Manages the movie detail view and its fragments
 */

public class MovieDetailActivity extends AppCompatActivity {

    private final Context context = MovieDetailActivity.this;
    private Movie mSelectedMovie;
    private boolean favorite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Sets up the view for us to bind data to. */
        final ActivityMovieDetailBinding detailBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        /* Extract the parcelable data from the intent and turn it back into a Movie object */
        mSelectedMovie = getIntent().getParcelableExtra(Constants.SELECTED_MOVIE_KEY);

        /* Load the backdrop */
        if (mSelectedMovie.getBackdropImagePath() != null) {

            URL backdropUrl = NetworkUtils
                    .getMovieImageUrl(Constants
                    .IMAGE_SIZE_XLARGE, mSelectedMovie.getBackdropImagePath());

            if (backdropUrl != null) {
                String backDrop = backdropUrl.toString();

                Picasso.get()
                        .load(backDrop)
                        .into(detailBinding.movieDetailTrailerThumbnailIv);
            }
        }

        /* Load the poster */
        if (mSelectedMovie.getPosterImagePath() != null) {
            URL posterUrl = NetworkUtils.getMovieImageUrl(Constants
                    .IMAGE_SIZE_SMALL, mSelectedMovie.getPosterImagePath());

            if (posterUrl != null) {
                String poster = posterUrl.toString();

                Picasso.get()
                        .load(poster)
                        .into(detailBinding.movieDetailPosterSmallIv);
            }
        }

        /* Set the title */
        detailBinding.movieDetailTitleTv
                .setText(mSelectedMovie.getOriginalTitle());

        /* Set the rating */
        detailBinding.movieDetailVoteAverageTv
                .setText(String.valueOf(mSelectedMovie.getUserRating()));

        /* Set the year */
        String movieReleaseFullDate = mSelectedMovie.getMovieReleaseDate();
        String[] datePart = movieReleaseFullDate.split("-");
        String year = datePart[0];

        detailBinding.movieDetailReleaseYear.setText(year);

        /* Set the synopsis */
        detailBinding.movieDetailDescriptionTv.setText(mSelectedMovie.getMovieSynopsis());

        /* Setup the favorites button */
        favorite = FindFavorites.isFavorite(context, mSelectedMovie);

        if (favorite) {
            detailBinding.movieDetailFavoritesButton
                    .setImageResource(R.drawable.ic_favorite_black_24px);
        } else {
            detailBinding.movieDetailFavoritesButton
                    .setImageResource(R.drawable.ic_favorite_border_black_24px);
        }

        /* Set a clickListener to the favorites button */
        detailBinding.movieDetailFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If the movie is already in favorites, delete it
                if (favorite) {
                    FindFavorites.removeFavorite(context, mSelectedMovie);
                    detailBinding.movieDetailFavoritesButton
                            .setImageResource(R.drawable.ic_favorite_border_black_24px);

                    // If the movie is not in favorites, add it
                } else {
                    FindFavorites.addFavorite(context, mSelectedMovie);
                    detailBinding.movieDetailFavoritesButton
                            .setImageResource(R.drawable.ic_favorite_black_24px);
                }
            }
        });

        /* Add the reviews fragment */
        FragmentManager reviewManager = getSupportFragmentManager();
        FragmentTransaction reviewTransaction = reviewManager.beginTransaction();

        ReviewFragment reviewFragment = (ReviewFragment)
                reviewManager.findFragmentById(R.id.fragment_review_recycler_view);

        if (reviewFragment == null) reviewFragment = new ReviewFragment();

        reviewTransaction.replace(R.id.movie_detail_fragment_reviews_container_rv, reviewFragment)
                .commit();
        reviewFragment.setMovieId(mSelectedMovie.getMovieId());

        /* Add the videos fragment */
        FragmentManager videoManager = getSupportFragmentManager();
        FragmentTransaction videoTransaction = videoManager.beginTransaction();

        VideoFragment videoFragment = (VideoFragment)
                videoManager.findFragmentById(R.id.fragment_video_recycler_view);

        if (videoFragment == null) videoFragment = new VideoFragment();

        videoTransaction.replace(R.id.movie_detail_fragment_trailers_container, videoFragment)
                .commit();
        videoFragment.setMovieId(mSelectedMovie.getMovieId());
    }
}