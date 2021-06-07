package com.example.flicksapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    int movieId;
    double rating;
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    boolean isPopular;

    // Constructor accept a JSONObject and grab required fields (used in JsonArrayToMoviesList)
    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating =jsonObject.getDouble("vote_average");
        movieId=jsonObject.getInt("id");
        isPopular = checkPopularity();
    }
    // Iterates through JSONArray and constructs a Movie Object for each element in JSONArray
    public static List<Movie> JsonArrayToMoviesList(JSONArray jsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i=0; i< jsonArray.length();i++){
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }

    // chcek if a movie is popular  ( >5 stars = popular)
    private Boolean checkPopularity() {
        return rating >= 8;
    }
    // Getters for fields
    public String getPosterPath() {
        //hardcode image size from configuration api + append relative poster path
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath(){
        //hardcode image size from configuration api + append relative backdrop path
        return String.format("https://image.tmdb.org/t/p/w1280/%s",backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public int getMovieId() { return movieId; }

    public double getRating() { return rating; }

    public boolean isPopular() { return isPopular; }
}
