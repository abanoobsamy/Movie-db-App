package com.abanoob_samy.moviedbapp.Repositories;

import com.abanoob_samy.moviedbapp.AppExecutors;
import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.abanoob_samy.moviedbapp.Request.MovieApiClient;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MovieRepositories {

    /**
     * second call for method begin from here
     */

//    private MutableLiveData<List<MovieModel>> mMovies;

    private MovieApiClient movieApiClient;

    private static MovieRepositories instance;

    private String query;
    private int pageNumber;

    private MovieRepositories() {

        movieApiClient = MovieApiClient.getInstance();
    }

    public static MovieRepositories getInstance() {

        if (instance == null) {

            instance = new MovieRepositories();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMoviesLiveData() {

        return movieApiClient.getMoviesLiveDataApi();
    }

    public LiveData<List<MovieModel>> getPopularLiveData() {

        return movieApiClient.getPopularLiveData();
    }

    // Second call for this method
    public void searchMoviesApi(String query, int pageNumber) {

        this.query = query;
        this.pageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    // Second call for this method
    public void searchMoviesApiPopular(int pageNumber) {

        this.pageNumber = pageNumber;
        movieApiClient.searchMoviesApiPopular(pageNumber);
    }

    public void searchNextPage() {

        searchMoviesApi(query, pageNumber + 1);
    }
}
