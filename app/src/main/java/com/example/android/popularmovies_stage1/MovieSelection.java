package com.example.android.popularmovies_stage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieSelection extends AppCompatActivity {

    public static final String TAG = "MovieSelection";
    public static final String EXTRA_PARCEL = "com.example.android.popularmovies_stage1.parcel";
    public static final String EXTRA_ID = "com.example.android.popularmovies_stage1.id";

    RecyclerView mRecyclerView;
    MovieAdapter mAdapter;
    ArrayList<Movie> mMoviesList = new ArrayList<>();
    int mPageNumber;
    int mMethodFlag;


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
        mPageNumber = 1;
        mMethodFlag = 0;

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView view, int dx, int dy){
                super.onScrolled(view, dx, dy);
                if(dy > 0){
                    if(!mRecyclerView.canScrollVertically(1)){
                        new FetchMoviesTask().execute();
                    }
                }
            }
        });

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

    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView holderImageView;

        public MovieHolder(View movieView){
            super(movieView);
            holderImageView = (ImageView) movieView.findViewById(R.id.list_item_image_view);
            movieView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            Movie clickedMovie = mMoviesList.get(mRecyclerView.getChildAdapterPosition(view));
            Intent movieDetailsIntent = new Intent(getApplicationContext(), MovieDetails.class);
            movieDetailsIntent.putParcelableArrayListExtra(EXTRA_PARCEL, mMoviesList);
            movieDetailsIntent.putExtra(EXTRA_ID, clickedMovie.getID());
            startActivity(movieDetailsIntent);
        }


    }

    private void showPopUp(){
        View menuAnchor = findViewById(R.id.sort_options);
        PopupMenu popup = new PopupMenu(this, menuAnchor);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.sort_by_popular:
                        mMoviesList.clear();
                        mPageNumber = 1;
                        mMethodFlag = 0;
                        new FetchMoviesTask().execute();
                        return true;
                    case R.id.sort_by_top_rated:
                        mMoviesList.clear();
                        mPageNumber = 1;
                        mMethodFlag = 1;
                        new FetchMoviesTask().execute();
                        return true;
                    default:
                        return true;
                }
            }
        });
        popup.inflate(R.menu.sort_movies_options);
        popup.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sort_movies_parent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item){
        switch(item.getItemId()){
            case R.id.sort_options:
                showPopUp();
                return true;
            default:
                super.onOptionsItemSelected(item);
                return true;
        }
    }


    private class FetchMoviesTask extends AsyncTask<Void, Void, List<Movie>>{
        @Override
        protected List<Movie> doInBackground(Void... params){
            return new MovieFetcher().fetchMovies(mMethodFlag, mPageNumber);
        }

        @Override
        protected void onPostExecute(List<Movie> parsedMovies){
            mMoviesList.addAll(parsedMovies);
            mAdapter.notifyDataSetChanged();
            mPageNumber++;
        }
    }
}
