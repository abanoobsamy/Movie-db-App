package com.abanoob_samy.moviedbapp.Response;

import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// This class is for getting multiple movies (Movies list) - popular movies
public class MovieSearchResponse {

    //finding the movie object.
    @SerializedName("total_results")
    @Expose
    private int total_results;

    //finding the movie object.
    @SerializedName("results")
    @Expose
    private List<MovieModel> movieModel;

    public int getTotal_results() {
        return total_results;
    }

    public List<MovieModel> getMovieModel() {
        return movieModel;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_results=" + total_results +
                ", movieModel=" + movieModel +
                '}';
    }
}
