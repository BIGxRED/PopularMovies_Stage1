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

//    private static final String MOVIEDB_BASE_URL_DISCOVER =
//            "https://api.themoviedb.org/3/discover/movie";

    //Constants used for building the base URL
    private static final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String METHOD_MOVIE_POPULAR = "popular";
    private static final String METHOD_MOVIE_TOP_RATED = "top_rated";

    //Parameters to be used with the query
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_LANGUAGE = "language";
    private static final String PARAM_PAGE = "page";

    //Parameter values to be used when building the URL
//    private static final String PARAM_SORT_BY = "sort_by";
//    private static final String SORT_BY = "popularity.desc";
    private static final String API_KEY = "";
    private static final String LANGUAGE = "en-US";
    public static int page = 1;

    public static URL buildURL(){
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(METHOD_MOVIE_POPULAR)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, LANGUAGE)
                .appendQueryParameter(PARAM_PAGE, Integer.toString(page))
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

            Movie movie = new Movie(title, ID, posterPath, overview);
            Log.i(TAG, movie.getTitle() + " has been added to the array");
            moviesList.add(movie);

        }
    }

    public List<Movie> fetchMovies(){
        //TODO: Keep an eye out on this line of code, I fear that this may cause issues later on
        List<Movie> movies = new ArrayList<>();
        try {
            String httpResponse = getHTTPResponse(buildURL());
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
