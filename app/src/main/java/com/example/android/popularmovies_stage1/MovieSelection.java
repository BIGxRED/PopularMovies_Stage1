package com.example.android.popularmovies_stage1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieSelection extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MovieAdapter mAdapter;
    List<Movie> mMoviesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selection);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_selection);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MovieAdapter(mMoviesList);
        mRecyclerView.setAdapter(mAdapter);

        new FetchMoviesTask().execute();
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        private List<Movie> adapterMovies;

        public MovieAdapter(List<Movie> movies){
            adapterMovies = movies;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);

            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position){
            Movie currentMovie = adapterMovies.get(position);
            Picasso.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w185/" + currentMovie.getPosterPath())
                    .into(holder.holderImageView);
        }

        @Override
        public int getItemCount(){
            return adapterMovies.size();
        }

    }

    private class MovieHolder extends RecyclerView.ViewHolder{
        ImageView holderImageView;

        public MovieHolder(View movieView){
            super(movieView);
            holderImageView = (ImageView) movieView.findViewById(R.id.list_item_image_view);
        }


    }


    private class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>>{
        @Override
        protected List<Movie> doInBackground(Void... params){
            return new MovieFetcher().fetchMovies();
        }

        @Override
        protected void onPostExecute(List<Movie> parsedMovies){
            mMoviesList = parsedMovies;
//            mAdapter.notifyDataSetChanged();  Unsure of why this is not working as I expected?
            mAdapter = new MovieAdapter(mMoviesList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
