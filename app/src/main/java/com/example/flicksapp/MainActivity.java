package com.example.flicksapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flicksapp.adapters.MovieAdapter;
import com.example.flicksapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String  TAG = "MainActivity";
    // TODO ADD KEY
    public static final String  NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed ";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  replace the ActionBar title with customized XML
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        //initialize movies as empty list then modify when data is retrieved from API
        movies = new ArrayList<Movie>();

        //Create the adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        //Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);
        //set a Layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // create AyncHttpClient object and send JSON Request, JsonObject should be returned
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,statusCode+"-onSuccess");
                JSONObject  jsonObject = json.jsonObject;
                try {
                    // get JSONArray "results", send as parameter to Movie jsonArrayToMovieList to get a list of Movie Objects
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"RESULTS: "+ results.toString());
                    movies.addAll(Movie.JsonArrayToMoviesList(results));
                    Log.i(TAG,"MOVIES: "+ movies.size());
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG,"Hit JSON exception",e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,statusCode+"-onFailure");
            }
        });
    }
}