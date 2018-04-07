package com.example.peter.popularmovies2.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by peter on 21/03/2018.
 * This class holds a Review object and implements parcelable so we can move instances of it
 * between classes and saved instance state.
 */

public class Review implements Parcelable {

    public static final Parcelable.Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    // The author
    @SerializedName(value = "author", alternate = "mAuthor")
    private final String mAuthor;

    // The comment they left
    @SerializedName(value = "content", alternate = "mComment")
    private final String mComment;

    // The ID of the comment
    @SerializedName(value = "id", alternate = "mId")
    private final String mId;

    // The URL of where the comment can be viewed on-line
    @SerializedName(value = "url", alternate = "mUrl")
    private final String mUrl;

    /**
     * Constructor for Review object.
     * @param mAuthor   The author of the review
     * @param mComment  The comment left by the reviewer
     * @param mId       The reviews unique ID
     * @param mUrl      The URL of where the comment can be viewed on-line
     */
    public Review(String mAuthor, String mComment, String mId, String mUrl) {
        this.mAuthor = mAuthor;
        this.mComment = mComment;
        this.mId = mId;
        this.mUrl = mUrl;
    }

    private Review(Parcel in) {
        this.mAuthor = in.readString();
        this.mComment = in.readString();
        this.mId = in.readString();
        this.mUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mAuthor);
        out.writeString(mComment);
        out.writeString(mId);
        out.writeString(mUrl);
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getComment() {
        return mComment;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
