package com.example.android.popularmovies_stage1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = "MovieDetails";

    ImageView mMoviePoster;
    TextView mMovieTitle;
    TextView mMovieOverview;
    TextView mMovieReleaseDate;
    TextView mMovieVoteCount;
    TextView mMovieVoteAverage;
    Movie mReceivedMovie;
    ArrayList<Movie> mMoviesList;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() has been called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        Intent receivedIntent = getIntent();
        if(receivedIntent.hasExtra(MovieSelection.EXTRA_PARCEL)){
            mMoviesList = receivedIntent.getParcelableArrayListExtra(MovieSelection.EXTRA_PARCEL);

            for(int i = 0; i < mMoviesList.size(); i++){
                Movie currentMovie = mMoviesList.get(i);
                Log.i(TAG, currentMovie.getTitle() + " has been received"
                        + "\nID: " + Integer.toString(currentMovie.getID())
                        + "\nPoster path: " + currentMovie.getPosterPath()
                        + "\nOverview: " + currentMovie.getOverview()
                        + "\nRelease date: " + currentMovie.getReleaseDate()
                        + "\nVote count: " + Integer.toString(currentMovie.getVoteCount())
                        + "\nVote average: " + Float.toString(currentMovie.getVoteAverage())
                        + "\nBackdrop path: " + currentMovie.getBackdropPath() + "\n");
            }
        }
        else{
            mMoviesList = null;
            Log.i(TAG, "Movies list is null...");
        }

        int receivedMovieID = 0;
        if (receivedIntent.hasExtra(MovieSelection.EXTRA_ID))
            receivedMovieID = receivedIntent.getIntExtra(MovieSelection.EXTRA_ID, 0);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MoviePagerAdapter(this));

        for(int k = 0; k < mMoviesList.size(); k++){
            if(mMoviesList.get(k).getID() == receivedMovieID){
                mViewPager.setCurrentItem(k);
                break;
            }
        }

    }

    public class MoviePagerAdapter extends PagerAdapter{
        private Context mContext;

        public MoviePagerAdapter (Context context){
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.activity_movie_details,
                    collection, false);

            mMoviePoster = (ImageView) layout.findViewById(R.id.iv_movie_poster);
            mMovieTitle = (TextView) layout.findViewById(R.id.tv_movie_title);
            mMovieOverview = (TextView) layout.findViewById(R.id.tv_movie_overview);
            mMovieReleaseDate = (TextView) layout.findViewById(R.id.tv_movie_release_date);
            mMovieVoteCount = (TextView) layout.findViewById(R.id.tv_movie_vote_count);
            mMovieVoteAverage = (TextView) layout.findViewById(R.id.tv_movie_vote_average);

//            Intent receivedIntent = getIntent();
//            mReceivedMovie = null;
//            mMoviesList = null;
//            if(receivedIntent.hasExtra(MovieSelection.EXTRA_PARCEL)){
//                mMoviesList = receivedIntent.getParcelableArrayListExtra(MovieSelection.EXTRA_PARCEL);
//                mReceivedMovie = mMoviesList.get(position);
//            }
//
//            Log.i(TAG, mReceivedMovie.getTitle() + " has been received"
//                    + "\nID: " + Integer.toString(mReceivedMovie.getID())
//                    + "\nPoster path: " + mReceivedMovie.getPosterPath()
//                    + "\nOverview: " + mReceivedMovie.getOverview()
//                    + "\nRelease date: " + mReceivedMovie.getReleaseDate()
//                    + "\nVote count: " + Integer.toString(mReceivedMovie.getVoteCount())
//                    + "\nVote average: " + Float.toString(mReceivedMovie.getVoteAverage())
//                    + "\nBackdrop path: " + mReceivedMovie.getBackdropPath() + "\n");

            //USING BUNDLE

//            Intent receivedIntent = getIntent();
//            if(receivedIntent.hasExtra(MovieSelection.EXTRA_BUNDLE)){
//                Bundle data = receivedIntent.getBundleExtra(MovieSelection.EXTRA_BUNDLE);
//                mMoviesList = data.getParcelableArrayList(MovieSelection.EXTRA_PARCEL);
//
//                for(int i = 0; i < mMoviesList.size(); i++){
//                    Movie currentMovie = mMoviesList.get(i);
//                    Log.i(TAG, currentMovie.getTitle() + " has been received"
//                            + "\nID: " + Integer.toString(currentMovie.getID())
//                            + "\nPoster path: " + currentMovie.getPosterPath()
//                            + "\nOverview: " + currentMovie.getOverview()
//                            + "\nRelease date: " + currentMovie.getReleaseDate()
//                            + "\nVote count: " + Integer.toString(currentMovie.getVoteCount())
//                            + "\nVote average: " + Float.toString(currentMovie.getVoteAverage())
//                            + "\nBackdrop path: " + currentMovie.getBackdropPath() + "\n");
//                }
//            }
//            else{
//                mMoviesList = null;
//                Log.i(TAG, "Movies list is null...");
//            }

            mReceivedMovie = mMoviesList.get(position);

            Picasso.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w185/" + mReceivedMovie.getPosterPath())
                    .into(mMoviePoster);
            mMovieTitle.setText(mReceivedMovie.getTitle());
            mMovieOverview.setText(mReceivedMovie.getOverview());
            mMovieReleaseDate.setText(mReceivedMovie.getReleaseDate());
            mMovieVoteCount.setText("Vote count: " + mReceivedMovie.getVoteCount());
            mMovieVoteAverage.setText(Float.toString(mReceivedMovie.getVoteAverage()) + "/10");
            
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view){
            collection.removeView((View) view);
        }

        @Override
        public int getCount(){
            Log.i(TAG, "getCount() has been called");
            return mMoviesList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object){
            return view == object;
        }

    }
}
