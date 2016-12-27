package com.example.android.popularmovies_stage1;

/**
 * Created by mike on 12/26/16.
 */

public class Movie {

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

}
