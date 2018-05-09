package com.example.peter.popularmovies2.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.app.Constants;
import com.example.peter.popularmovies2.model.Movie;
import com.example.peter.popularmovies2.repository.MovieContract.MovieEntry;
import com.example.peter.popularmovies2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * This adapter uses a cursor as its data source.
 */
public class FavoritesAdapter extends
        RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder> {

    /* Used to access utility methods, app resources and layout inflaters */
    private final Context mContext;

    /* Returned database cursor */
    private Cursor mCursor;

    /* Click interface */
    final private FavoritesAdapterOnClickHandler mClickHandler;

    /* Constructor */
    public FavoritesAdapter(Context context, FavoritesAdapterOnClickHandler listener) {
        mContext = context;
        mClickHandler = listener;
    }

    @NonNull
    @Override
    public FavoritesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        int layoutForListItem = R.layout.poster_item_view;
        View view = LayoutInflater
                .from(mContext)
                .inflate(layoutForListItem, viewGroup, false);

        view.setFocusable(true);

        return new FavoritesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapterViewHolder favoritesAdapterViewHolder,
                                 int position) {

        mCursor.moveToPosition(position);

        String movieTitle = mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_TITLE));
        String imagePath = mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH));

        favoritesAdapterViewHolder.movieTitleTextView.setText(movieTitle);

        if (imagePath.length() == 0) {

            // Swap the visibilities of the various views
            favoritesAdapterViewHolder.listItemImageView.setVisibility(View.GONE);
            favoritesAdapterViewHolder.noPosterAvailableTextView.setVisibility(View.VISIBLE);
            favoritesAdapterViewHolder.listItemNoImageImageView.setVisibility(View.VISIBLE);

            // Set the image to be a tMDB logo
            favoritesAdapterViewHolder.listItemNoImageImageView
                    .setImageDrawable(mContext.getResources()
                            .getDrawable(R.drawable.ic_powered_by_rectangle_green));

            favoritesAdapterViewHolder.noPosterAvailableTextView
                    .setText(R.string.no_poster);
        } else {

            /* If a valid movie poster URL endpoint is available,
             * display the movies poster in the current ViewHolder
             */
            favoritesAdapterViewHolder.listItemNoImageImageView.setVisibility(View.GONE);
            favoritesAdapterViewHolder.noPosterAvailableTextView.setVisibility(View.INVISIBLE);
            favoritesAdapterViewHolder.listItemImageView.setVisibility(View.VISIBLE);

            Picasso.get()
                    .load(String.valueOf(NetworkUtils.getMovieImageUrl
                            (Constants.IMAGE_SIZE_MEDIUM, imagePath)))
                    .placeholder(R.drawable.ic_powered_by_rectangle_blue).fit()
                    .into(favoritesAdapterViewHolder.listItemImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    /* Called by the controlling Activity or Fragment to replace the underlying data set */
    public void swapCursor(Cursor newCursor){
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    /* The interface that receives onClick messages. */
    public interface FavoritesAdapterOnClickHandler {
        void onClick(Movie clickedMovie);
    }

    /**
     * A ViewHolder is a required as part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a list / grid item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    class FavoritesAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        final ImageView listItemImageView;
        final ImageView listItemNoImageImageView;
        final TextView movieTitleTextView;
        final TextView noPosterAvailableTextView;

        FavoritesAdapterViewHolder(View itemView){
            super(itemView);

            listItemImageView = itemView.findViewById(R.id.poster_iv);
            listItemNoImageImageView = itemView.findViewById(R.id.no_poster_available_iv);
            movieTitleTextView = itemView.findViewById(R.id.movie_title_poster_tv);
            noPosterAvailableTextView = itemView.findViewById(R.id.no_poster_available_tv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mCursor.moveToPosition(clickedPosition);

            Movie currentMovie = new Movie(
                    mCursor.getInt(mCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID)),
                    mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_TITLE)),
                    mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_ORIGINAL_TITLE)),
                    mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH)),
                    mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_BACKDROP_PATH)),
                    mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)),
                    mCursor.getDouble(mCursor.getColumnIndex(MovieEntry.COLUMN_RATING)),
                    mCursor.getString(mCursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_YEAR)));

            mClickHandler.onClick(currentMovie);
        }
    }
}
