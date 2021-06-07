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


public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<Movie> movies;

    // used for hetergeneous layout
    private final int POPULAR = 1;

    // Constructor will receive context and list of movies
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // used for hetergeneous layout
    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).isPopular()) {
            return POPULAR;
        }
        return -1;
    }

    // inflates layout from .XML layout and return the holder.
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        //inflate view from context
        RecyclerView.ViewHolder movieView;
        // used for hetergeneous layout
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case POPULAR:
                View v1 = inflater.inflate(R.layout.item_movie_popular, parent, false);
                movieView = new ViewHolder_popular(v1);
                break;
            default: //sucky
                View v = inflater.inflate(R.layout.item_movie, parent, false);
                movieView = new ViewHolder_sucky(v);
                break;
        }
        return movieView;
    }

    // Populates data into the item through the holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder"+position);
        Movie movie = movies.get(position);
        switch (holder.getItemViewType()) {
            case POPULAR:
                ViewHolder_popular vh1 = (ViewHolder_popular) holder;
                vh1.bind(movie);
                break;
            default:
                ViewHolder_sucky vh2 = (ViewHolder_sucky) holder;
                vh2.bind(movie);
                break;
        }
    }

    // describes an item view and metadata about its place withing the RV view
    // object represents each item on the collection and used to display it
    public class ViewHolder_sucky extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RatingBar rb_voteAverage;

        // grab views
        public ViewHolder_sucky(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            rb_voteAverage = itemView.findViewById(R.id.rb_voteAverage);
        }

        // bind data to views
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getPosterPath();
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
    public class ViewHolder_popular extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView iv_popular;

        // grab views
        public ViewHolder_popular(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            iv_popular = itemView.findViewById(R.id.iv_popular);
            tvOverview = itemView.findViewById(R.id.tvOverview);
        }

        // bind data to views
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            iv_popular.setVisibility(View.VISIBLE);
            String imageUrl = movie.getBackdropPath();
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                tvOverview.setText(movie.getOverview());

            }
            //placeholder image shown while requested is in process
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(ivPoster);
        }
    }
}
