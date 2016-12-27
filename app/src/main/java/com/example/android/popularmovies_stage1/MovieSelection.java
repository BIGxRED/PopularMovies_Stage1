package com.example.android.popularmovies_stage1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MovieSelection extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MovieAdapter mAdapter;

    private static final int NUMBER_OF_MOVIES = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selection);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_selection);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MovieAdapter(NUMBER_OF_MOVIES);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        private int mNumberOfMovies;

        public MovieAdapter(int numberOfMovies){
            mNumberOfMovies = numberOfMovies;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);

            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position){

        }

        @Override
        public int getItemCount(){
            return mNumberOfMovies;
        }

    }

    private class MovieHolder extends RecyclerView.ViewHolder{

        public MovieHolder(View movieView){
            super(movieView);
        }


    }
}
