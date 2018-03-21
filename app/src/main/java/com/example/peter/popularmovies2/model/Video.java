package com.example.peter.popularmovies2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by peter on 21/03/2018.
 * Manages a Video object (trailer, movie clip, etc.) that is associated with a movie.
 */

public class Video implements Parcelable {

    /* Parcelable CREATOR */
    public static final Creator<Video> CREATOR = new Creator<Video>() {
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

    /* The movies ID */
    private final int mId;
    /* The video clip's ID */
    private final String mVideoId;
    /* The videos key */
    private final String mVideoKey;
    /* The videos name */
    private final String mVideoName;
    /* The site where the movie resides */
    private final String mVideoSite;
    /* The movies size (pixels on the screen) */
    private final int mVideoSize;
    /* The videos type */
    private final String mVideoType;

    public Video (int id, String videoId, String videoKey, String videoName, String videoSite, int videoSize, String videoType) {
        mId = id;
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
        this.mId = in.readInt();
        this.mVideoId = in.readString();
        this.mVideoKey = in.readString();
        this.mVideoName = in.readString();
        this.mVideoSite = in.readString();
        this.mVideoSize = in.readInt();
        this.mVideoType = in.readString();
    }

    /* Returns the movie ID */
    public int getMovieId() {
        return mId;
    }

    /* Returns the video ID */
    public String getVideoId() {
        return mVideoId;
    }

    /* Returns the video key */
    public String getVideoKey() {
        return mVideoKey;
    }

    /* Returns the video name */
    public String getVideoName() {
        return mVideoName;
    }

    /* Returns the video site */
    public String getVideoSite() {
        return mVideoSite;
    }

    /* Returns the video size (in pixels) */
    public int getVideoSize() {
        return mVideoSize;
    }

    /* Returns the video's type */
    public String getVideoType() {
        return mVideoType;
    }

    /* Used by Parcelable */
    @Override
    public int describeContents() {
        return 0;
    }

    /* This is where we write the values we want to save to the parcel */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mVideoId);
        out.writeString(mVideoKey);
        out.writeString(mVideoName);
        out.writeString(mVideoSite);
        out.writeInt(mVideoSize);
        out.writeString(mVideoType);
    }
}
