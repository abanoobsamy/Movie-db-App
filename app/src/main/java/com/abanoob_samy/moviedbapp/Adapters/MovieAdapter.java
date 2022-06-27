package com.abanoob_samy.moviedbapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.abanoob_samy.moviedbapp.R;
import com.abanoob_samy.moviedbapp.Utils.Credentials;
import com.abanoob_samy.moviedbapp.databinding.MovieCustomBinding;
import com.abanoob_samy.moviedbapp.databinding.PopularCustomBinding;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter {

    private List<MovieModel> movieModels;

    private OnMovieClickListener onMovieClickListener;

    private static final int DISPLAY_POP = 1;
    private static final int DISPLAY_SEARCH = 2;

    public MovieAdapter(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setMovieModels(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.movie_custom, parent, false), onMovieClickListener);

        View view = null;

        if(viewType == DISPLAY_SEARCH) {
            view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_custom, parent, false);

            return new MovieViewHolder(view, onMovieClickListener);
        }
        else {
            view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_custom, parent, false);

            return new PopularViewHolder(view, onMovieClickListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MovieModel movieModel = movieModels.get(position);

//        holder.binding.tvTitle.setText(movieModel.getTitle());
//        holder.binding.tvReleaseDate.setText(movieModel.getRelease_date());
//        holder.binding.tvDuration.setText(movieModel.getOriginal_language());

//        holder.binding.ratingBar.setRating((movieModel.getVote_average()) / 2);

//        Glide.with(holder.itemView.getContext())
//                .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
//                .into(holder.binding.ivMovie);

        int itemView = getItemViewType(position);

        if (itemView == DISPLAY_SEARCH) {

            ((MovieViewHolder)holder).binding.tvTitle.setText(movieModel.getTitle());
            ((MovieViewHolder)holder).binding.tvReleaseDate.setText(movieModel.getRelease_date());
            ((MovieViewHolder)holder).binding.tvDuration.setText(movieModel.getOriginal_language());
            ((MovieViewHolder)holder).binding.ratingBar.setRating((movieModel.getVote_average()) / 2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(((MovieViewHolder)holder).binding.ivMovie);
        }
        else {
            ((PopularViewHolder)holder).binding.ratingBarPopular.setRating((movieModel.getVote_average()) / 2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(((PopularViewHolder)holder).binding.ivMoviePopular);

        }
    }

    @Override
    public int getItemCount() {

        if (movieModels != null) {
            return movieModels.size();
        }

        return 0;
    }

    public MovieModel getSelectedMovieModel(int position) {

        if (movieModels != null) {
            if (movieModels.size() > 0) {
                return movieModels.get(position);
            }
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {

        if (Credentials.POPULAR) {

            return DISPLAY_POP;
        }
        else {
            return DISPLAY_SEARCH;
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnMovieClickListener onMovieClickListener;
        MovieCustomBinding binding;

        public MovieViewHolder(@NonNull View itemView, OnMovieClickListener onMovieClickListener) {
            super(itemView);

            this.onMovieClickListener = onMovieClickListener;

            binding = MovieCustomBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            onMovieClickListener.onMovieClick(getAdapterPosition());
        }
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnMovieClickListener onMovieClickListener;
        PopularCustomBinding binding;

        public PopularViewHolder(@NonNull View itemView, OnMovieClickListener onMovieClickListener) {
            super(itemView);

            this.onMovieClickListener = onMovieClickListener;

            binding = PopularCustomBinding.bind(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            onMovieClickListener.onMovieClick(getAdapterPosition());
        }
    }
}
