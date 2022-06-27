package com.abanoob_samy.moviedbapp.Response;

import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// This class is for single movie request
public class MovieResponse {

    //finding the movie object.
    @SerializedName("results")
    @Expose
    private MovieModel movieModel;

    public MovieModel getMovieModel() {
        return movieModel;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieModel=" + movieModel +
                '}';
    }
}
