package com.example.android.popularmovies_stage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = "MovieDetails";

    ImageView mMoviePoster;
    TextView mMovieTitle;
    TextView mMovieOverview;
    TextView mMovieReleaseDate;
    TextView mMovieVoteCount;
    TextView mMovieVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        mMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        mMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);
        mMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        mMovieVoteCount = (TextView) findViewById(R.id.tv_movie_vote_count);
        mMovieVoteAverage = (TextView) findViewById(R.id.tv_movie_vote_average);

        Intent receivedIntent = getIntent();
        Movie receivedMovie = null;
        if(receivedIntent.hasExtra(MovieSelection.EXTRA_PARCEL)){
            receivedMovie = receivedIntent.getParcelableExtra(MovieSelection.EXTRA_PARCEL);
        }
        Picasso.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185/" + receivedMovie.getPosterPath())
                .into(mMoviePoster);
        mMovieTitle.setText(receivedMovie.getTitle());
        mMovieOverview.setText(receivedMovie.getOverview());
        mMovieReleaseDate.setText(receivedMovie.getReleaseDate());
        mMovieVoteCount.setText("Vote count: " + receivedMovie.getVoteCount());
        mMovieVoteAverage.setText(Float.toString(receivedMovie.getVoteAverage()) + "/10");
    }
}
