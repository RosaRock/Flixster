package com.example.flicksapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    String posterPath;
    String backdropPath;
    String title;
    String overview;

    // Constructor accept a JSONObject and grab required fields (used in JsonArrayToMoviesList)
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }
    // Iterates through JSONArray and constructs a Movie Object for each element in JSONArray
    public static List<Movie> JsonArrayToMoviesList(JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i=0; i< jsonArray.length();i++){
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }

    // Getters for fields
    public String getPosterPath() {
        //hardcode image size from configuration api + append relative poster path
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        //hardcode image size from configuration api + append relative backdrop path
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
