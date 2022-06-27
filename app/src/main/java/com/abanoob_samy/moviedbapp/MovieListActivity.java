package com.abanoob_samy.moviedbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abanoob_samy.moviedbapp.Adapters.MovieAdapter;
import com.abanoob_samy.moviedbapp.Adapters.OnMovieClickListener;
import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.abanoob_samy.moviedbapp.Request.Service;
import com.abanoob_samy.moviedbapp.Response.MovieSearchResponse;
import com.abanoob_samy.moviedbapp.Utils.Credentials;
import com.abanoob_samy.moviedbapp.Utils.MovieApi;
import com.abanoob_samy.moviedbapp.ViewModel.MovieViewModel;
import com.abanoob_samy.moviedbapp.databinding.ActivityMovieListBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity implements OnMovieClickListener {

    /**
     * fourth and latest call for method begin from here
     */

    private static final String TAG = "MovieListActivity";
    private ActivityMovieListBinding binding;

    private MovieViewModel movieViewModel;
    private MovieAdapter adapter;

    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpSearchView();

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        setSupportActionBar(binding.toolbar);

        configurationRecyclerView();
        observePopular();

        observeAnyChange();


        movieViewModel.searchMoviesApiPopular(1);
    }

    private void configurationRecyclerView() {

        adapter = new MovieAdapter(this);

        binding.recyclerViewMovie.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.recyclerViewMovie.setAdapter(adapter);

        binding.recyclerViewMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if (binding.recyclerViewMovie.canScrollVertically(1)) {

                    movieViewModel.searchNextPage();
                }
            }
        });
    }

    // Fourth call for this method
    public void searchMoviesApi(String query, int pageNumber) {

        movieViewModel.searchMoviesApi(query, pageNumber);
    }

    private void setUpSearchView() {

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                movieViewModel.searchMoviesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isPopular = false;
            }
        });
    }

    private void observePopular() {

        movieViewModel.getPopular().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                //observing for any data change
                if (movieModels != null) {

                    for (MovieModel movieModel: movieModels) {

                        // get the data in log.
                        Log.d(TAG, "onChanged: Title: " + movieModel.getTitle());

                        adapter.setMovieModels(movieModels);
                    }
                }
            }
        });
    }

    private void observeAnyChange() {

        movieViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                //observing for any data change
                if (movieModels != null) {

                    for (MovieModel movieModel: movieModels) {

                        // get the data in log.
                        Log.d(TAG, "onChanged: Title: " + movieModel.getTitle());

                        adapter.setMovieModels(movieModels);
                    }
                }
            }
        });
    }

    private void getRetrofitResponse() {

        MovieApi movieApi = Service.getMovieApi();

        Call<MovieSearchResponse> responseCall =
                movieApi.MOVIE_SEARCH_RESPONSE_CALL(Credentials.API_KEY, "Action", 1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if (response.code() == 200) {

                    Log.d(TAG, "onResponse: " + response.body().toString());

                    List<MovieModel> movieModels = new ArrayList<>(response.body().getMovieModel());

                    for (MovieModel movieModel: movieModels) {

                        Log.d(TAG, "onResponse: " + movieModel.getRelease_date());
                    }
                }
                else {

                    try {
                        Log.d(TAG, "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    private void getRetrofitResponseAccordingToId() {

        MovieApi movieApi = Service.getMovieApi();

        Call<MovieModel> responseCall =
                movieApi.MOVIE_MODEL_CALL(550, Credentials.API_KEY);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if (response.code() == 200) {

                    MovieModel movieModel = response.body();

                    Log.d(TAG, "onResponse: " + movieModel.getTitle());

                }
                else {

                    try {
                        Log.d(TAG, "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMovieClick(int position) {

        Toast.makeText(getApplicationContext(), "The Position is: " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movie", adapter.getSelectedMovieModel(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }
}