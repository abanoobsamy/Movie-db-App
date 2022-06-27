package com.abanoob_samy.moviedbapp.ViewModel;

import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.abanoob_samy.moviedbapp.Repositories.MovieRepositories;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovieViewModel extends ViewModel {

    /**
     * third call for method begin from here
     */

    private MovieRepositories movieRepositories;

    public MovieViewModel() {

        movieRepositories = MovieRepositories.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {

        return movieRepositories.getMoviesLiveData();
    }

    public LiveData<List<MovieModel>> getPopular() {

        return movieRepositories.getPopularLiveData();
    }

    // Third call for this method
    public void searchMoviesApi(String query, int pageNumber) {

        movieRepositories.searchMoviesApi(query, pageNumber);
    }

    public void searchMoviesApiPopular(int pageNumber) {

        movieRepositories.searchMoviesApiPopular(pageNumber);
    }

    public void searchNextPage() {

        movieRepositories.searchNextPage();
    }
}
