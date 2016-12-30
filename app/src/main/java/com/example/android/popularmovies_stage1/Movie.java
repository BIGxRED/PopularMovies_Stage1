package com.example.android.popularmovies_stage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mike on 12/26/16.
 */

public class Movie implements Parcelable {

    String mTitle;
    int mID;
    String mPosterPath;
    String mOverview;

    public Movie(String title, int ID, String posterPath, String overview){
        mTitle = title;
        mID = ID;
        mPosterPath = posterPath;
        mOverview = overview;
    }

    public Movie(Parcel in){
        mTitle = in.readString();
        mID = in.readInt();
        mPosterPath = in.readString();
        mOverview = in.readString();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    @Override
    public String toString(){
        return mOverview;
    }

    //Methods required for the parcelable interface
    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out,int flags){
        out.writeString(mTitle);
        out.writeInt(mID);
        out.writeString(mPosterPath);
        out.writeString(mOverview);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }

        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };

}
