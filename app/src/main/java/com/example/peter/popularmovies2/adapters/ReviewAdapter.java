package com.example.peter.popularmovies2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import com.example.peter.popularmovies2.R;
import com.example.peter.popularmovies2.model.Review;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private final Context mContext;
    private final ArrayList<Review> mReviews;

    public ReviewAdapter(Context context) {
        mContext = context;
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
        reviewAdapterViewHolder.reviewAuthorTextView.setText(currentReview.getAuthor());
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

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView reviewTextView;
        final TextView reviewAuthorTextView;

        ReviewAdapterViewHolder(View itemView) {
            super(itemView);

            reviewAuthorTextView = itemView.findViewById(R.id.review_list_item_author_text_view);
            reviewTextView = itemView.findViewById(R.id.review_list_item_comment_text_view);
        }
    }
}
