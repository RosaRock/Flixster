package com.example.flicksapp.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flicksapp.R;
import com.example.flicksapp.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    // Constructor will receive context and list of movies
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // inflates layout from .XML layout and return the holder.
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        //inflate view from context
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(movieView);
    }

    // Populates data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder"+position);
        //get the movie at the passed position
        Movie movie = movies.get(position);
        //Bind the movie data into the view holder
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // describes an item view and metadata about its place withing the RV view
    // object represents each item on the collection and used to display it
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        // grab views
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        // bind data to views
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageUrl = movie.getBackdropPath();
            else
                imageUrl = movie.getPosterPath();
            Glide.with(context).load(imageUrl).into(ivPoster);
        }
    }
}
