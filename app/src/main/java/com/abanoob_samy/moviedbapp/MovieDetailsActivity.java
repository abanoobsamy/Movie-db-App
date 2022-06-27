package com.abanoob_samy.moviedbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.abanoob_samy.moviedbapp.databinding.ActivityMovieDetailsBinding;
import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDataFromIntent();
    }

    private void getDataFromIntent() {

        if (getIntent().hasExtra("movie")) {

            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            binding.tvMovieTitleDetails.setText(movieModel.getTitle());
            binding.tvMovieDurationDetails.setText(movieModel.getOriginal_language());
            binding.tvMovieOverviewDetails.setText(movieModel.getMovie_overview());
            binding.ratingBarDetails.setRating((movieModel.getVote_average()) / 2);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(binding.ivMovieDetails);

        }
    }
}