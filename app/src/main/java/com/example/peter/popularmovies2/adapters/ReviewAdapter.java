package com.example.peter.popularmovies2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private final Context mContext;
    private final ReviewAdapterOnClickHandler mClickHandler;
    private final ArrayList<Review> mReviews;

    public ReviewAdapter(Context context, ReviewAdapterOnClickHandler listener) {
        mContext = context;
        mClickHandler = listener;
        mReviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                    int viewType) {

        int layoutIdForListItem = R.layout.review_list_item;
        View view = LayoutInflater.from(mContext).inflate(layoutIdForListItem,
                viewGroup, false);
        view.setFocusable(true);

        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewAdapterViewHolder
                                         reviewAdapterViewHolder, int position) {

        Review currentReview = mReviews.get(position);
//        String movieId = currentReview.getId();
//        String author = currentReview.getAuthor();
//        String comment = currentReview.getComment();
//        String reviewUrl = currentReview.getUrl();

        reviewAdapterViewHolder.reviewTextView.setText(currentReview.getComment());

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void updateReviews(ArrayList<Review> reviews) {
        if (reviews != null && !reviews.isEmpty()) {
            mReviews.clear();
            mReviews.addAll(reviews);
        } else {
            mReviews.clear();
        }
        notifyDataSetChanged();
    }

    public interface ReviewAdapterOnClickHandler {
        void onClick(Review review, int adapterPosition);
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView reviewTextView;

        ReviewAdapterViewHolder(View itemView) {
            super(itemView);

            reviewTextView = itemView.findViewById(R.id.review_list_item_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Review currentReview = mReviews.get(clickedPosition);
            mClickHandler.onClick(currentReview, clickedPosition);
        }
    }
}
