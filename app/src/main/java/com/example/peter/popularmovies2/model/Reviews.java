package com.example.peter.popularmovies2.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by peter on 21/03/2018.
 * This class holds a Review object and implements parcelable so we can move instances of it
 * between classes and saved instance state.
 */

public class Reviews implements Parcelable {
    protected Reviews(Parcel in) {
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
