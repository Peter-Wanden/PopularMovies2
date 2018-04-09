package com.example.peter.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by peter on 21/03/2018.
 * Manages a Video object (trailer, movie clip, etc.) that is associated with a movie.
 */

public class Video implements Parcelable {

    /* Parcelable CREATOR */
    public static final Parcelable.Creator<Video> CREATOR = new Creator<Video>() {
        /* This calls the private 'Video(Parcel in)' constructor and passes along the Parcel
         * and returns a new object.
         */
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        /* Can pass an array of this class */
        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    /* The movies ID - this is not part of the Gson Json object and is set after creation */
    private int mMovieId;

    /* The video clip's ID */
    @SerializedName(value = "id", alternate = "mVideoId")
    private final String mVideoId;

    /* The videos key */
    @SerializedName(value = "key", alternate = "mVideoKey")
    private final String mVideoKey;

    /* The videos type */
    @SerializedName(value = "name", alternate = "mVideoName")
    private final String mVideoName;

    /* The site where the movie resides */
    @SerializedName(value = "site", alternate = "mVideoSite")
    private final String mVideoSite;

    /* The movies size (pixels on the screen) */
    @SerializedName(value = "size", alternate = "mVideoSize")
    private final int mVideoSize;

    /* The videos type */
    @SerializedName(value = "type", alternate = "mVideoType")
    private final String mVideoType;

    public Video (int movieId, String videoId, String videoKey, String videoName, String videoSite, int videoSize, String videoType) {
        mMovieId = movieId;
        mVideoId = videoId;
        mVideoKey = videoKey;
        mVideoName = videoName;
        mVideoSite = videoSite;
        mVideoSize = videoSize;
        mVideoType = videoType;
    }

    /* This is where we retrieve the values that were originally written to the parcel.
     * This constructor is usually private so that only the CREATOR can access it.
     */
    private Video(Parcel in) {
        this.mMovieId = in.readInt();
        this.mVideoId = in.readString();
        this.mVideoKey = in.readString();
        this.mVideoName = in.readString();
        this.mVideoSite = in.readString();
        this.mVideoSize = in.readInt();
        this.mVideoType = in.readString();
    }

    /* Returns the video key */
    public String getVideoKey() {
        return mVideoKey;
    }

    /* Used by Parcelable */
    @Override
    public int describeContents() {
        return 0;
    }

    /* This is where we write the values we want to save to the parcel */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mMovieId);
        out.writeString(mVideoId);
        out.writeString(mVideoKey);
        out.writeString(mVideoName);
        out.writeString(mVideoSite);
        out.writeInt(mVideoSize);
        out.writeString(mVideoType);
    }
}
