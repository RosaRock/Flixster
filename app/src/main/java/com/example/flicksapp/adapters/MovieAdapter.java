package com.example.flicksapp.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flicksapp.R;
import com.example.flicksapp.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


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
        RatingBar rb_voteAverage;
        ImageView iv_popular;

        // grab views
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            rb_voteAverage = itemView.findViewById(R.id.rb_voteAverage);
            iv_popular = itemView.findViewById(R.id.iv_popular);
        }

        // bind data to views
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            if (movie.isPopular()) {
                iv_popular.setVisibility(View.VISIBLE);
            }

            String imageUrl;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
                rb_voteAverage.setRating((float)movie.getRating());
            }
            else
                imageUrl = movie.getPosterPath();

            //placeholder image shown while requested is in process
            int radius = 20; // corner radius, higher value = more rounded
            int margin = 5; // crop margin, set to 0 for corners with no crop
            Glide.with(context)
                    .load(imageUrl)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(ivPoster);
        }
    }
}
