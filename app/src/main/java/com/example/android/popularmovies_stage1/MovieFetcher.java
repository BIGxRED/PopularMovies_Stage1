package com.example.android.popularmovies_stage1;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by mike on 12/26/16.
 */

public class MovieFetcher {
    private static final String TAG = "MovieFetcher";

    //Constants used for building the base URL
    private static final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String METHOD_MOVIE_POPULAR = "popular";
    private static final String METHOD_MOVIE_TOP_RATED = "top_rated";

    //Parameters to be used with the query
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_PAGE = "page";

    //Parameter values to be used when building the URL
    private static final String API_KEY = "";
    private static final String LANGUAGE = "en-US";
//    public static int page = 1;

    public static URL buildURL(int methodFlag, int pageNumber){
        String queryMethod;
        switch (methodFlag){
            case 0:
                queryMethod = METHOD_MOVIE_POPULAR;
                break;
            case 1:
                queryMethod = METHOD_MOVIE_TOP_RATED;
                break;
            default:
                queryMethod = METHOD_MOVIE_POPULAR;
                break;
        }

        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(queryMethod)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAM_PAGE, Integer.toString(pageNumber))
                .build();

        URL url = null;

        try{
            url = new URL(builtUri.toString());
            Log.i(TAG, "The resulting built URL: " + url.toString());
        }
        catch (MalformedURLException mue){
            mue.printStackTrace();
        }

        return url;
    }


    public static String getHTTPResponse(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext())
                return scanner.next();
            else
                return null;
        }
        finally {
            connection.disconnect();
        }
    }

    private void parseMovies(String httpResponse, List moviesList) throws JSONException{
        JSONObject jsonRoot = new JSONObject(httpResponse);
        JSONArray jsonResults = jsonRoot.getJSONArray("results");

        for (int i = 0; i < jsonResults.length(); i++){
            JSONObject jsonMovie = jsonResults.getJSONObject(i);
            String title = jsonMovie.getString("title");
            int ID = jsonMovie.getInt("id");
            String posterPath = jsonMovie.getString("poster_path");
            String overview = jsonMovie.getString("overview");
            String releaseDate = jsonMovie.getString("release_date");
            int voteCount = jsonMovie.getInt("vote_count");
            //getDouble() is being used in this case because a double is essentially a float, only
            //with a higher precision (more decimal values); therefore, the correct value should be
            //obtained if the returned double is cast into a float
            float voteAverage = (float) jsonMovie.getDouble("vote_average");
            String backdropPath = jsonMovie.getString("backdrop_path");

            Movie movie = new Movie(title, ID, posterPath, overview, releaseDate, voteCount,
                    voteAverage, backdropPath);
            Log.i(TAG, movie.getTitle() + " has been added to the array"
                    + "\nID: " + Integer.toString(movie.getID())
                    + "\nPoster path: " + movie.getPosterPath()
                    + "\nOverview: " + movie.getOverview()
                    + "\nRelease date: " + movie.getReleaseDate()
                    + "\nVote count: " + Integer.toString(movie.getVoteCount())
                    + "\nVote average: " + Float.toString(movie.getVoteAverage())
                    + "\nBackdrop path: " + movie.getBackdropPath() + "\n");
            moviesList.add(movie);

        }
    }

    public List<Movie> fetchMovies(int methodFlag, int pageNumber){
        //TODO: Keep an eye out on this line of code, I fear that this may cause issues later on
        List<Movie> movies = new ArrayList<>();
        try {
            String httpResponse = getHTTPResponse(buildURL(methodFlag, pageNumber));
            parseMovies(httpResponse, movies);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        catch(JSONException je){
            je.printStackTrace();
        }
        return movies;
    }
}
