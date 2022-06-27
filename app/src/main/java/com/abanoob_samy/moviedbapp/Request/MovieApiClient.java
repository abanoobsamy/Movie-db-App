package com.abanoob_samy.moviedbapp.Request;

import android.util.Log;

import com.abanoob_samy.moviedbapp.AppExecutors;
import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.abanoob_samy.moviedbapp.Response.MovieSearchResponse;
import com.abanoob_samy.moviedbapp.Utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    /**
     * first call for method begin from here
     */

    private static final String TAG = "MovieApiClient";

    private static MovieApiClient instance;

    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    private RetrieveMoviesRunnablePopular retrieveMoviesRunnablePopular;

    private MutableLiveData<List<MovieModel>> mMovies;

    private MutableLiveData<List<MovieModel>> mPopular;

    private MovieApiClient() {

        mMovies = new MutableLiveData<>();
        mPopular = new MutableLiveData<>();
    }

    public static MovieApiClient getInstance() {

        if (instance == null) {

            instance = new MovieApiClient();
        }

        return instance;
    }

    public LiveData<List<MovieModel>> getMoviesLiveDataApi() {
        return mMovies;
    }

    public LiveData<List<MovieModel>> getPopularLiveData() {
        return mPopular;
    }

    // First call for this method
    public void searchMoviesApi(String query, int pageNumber) {

        if (retrieveMoviesRunnable != null) {

            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        Future myHandler = AppExecutors.getInstance().getExecutorService().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {

                //Canceling the retrofit call.
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    //for popular
    public void searchMoviesApiPopular(int pageNumber) {

        if (retrieveMoviesRunnablePopular != null) {

            retrieveMoviesRunnablePopular = null;
        }

        retrieveMoviesRunnablePopular = new RetrieveMoviesRunnablePopular(pageNumber);

        Future myHandler2 = AppExecutors.getInstance().getExecutorService().submit(retrieveMoviesRunnablePopular);

        AppExecutors.getInstance().getExecutorService().schedule(new Runnable() {
            @Override
            public void run() {

                //Canceling the retrofit call.
                myHandler2.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    // to retrieve data from RESTAPI by runnable class.
    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        private boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            try {

                Response response = getMovieSearchResponseCall(query, pageNumber).execute();

                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {

                    List<MovieModel> movieModels =
                            new ArrayList<>(((MovieSearchResponse)response.body()).getMovieModel());

                    if (pageNumber == 1) {

                        mMovies.postValue(movieModels);
                    }
                    else {

                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(movieModels);

                    }
                }
                else {

                    String error = response.errorBody().string();
                    Log.d(TAG, "Error: " + error);
                    mMovies.postValue(null);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        private Call<MovieSearchResponse> getMovieSearchResponseCall(String query, int pageNumber) {

            return Service.getMovieApi().MOVIE_SEARCH_RESPONSE_CALL(Credentials.API_KEY, query, pageNumber);
        }

        private void setCancelRequest() {

            Log.d(TAG, "setCancelRequest: Canceling Search Request.");
            cancelRequest = true;
        }

    }

    // to retrieve data from RESTAPI by runnable class.
    private class RetrieveMoviesRunnablePopular implements Runnable {

        private int pageNumber;
        private boolean cancelRequest;

        public RetrieveMoviesRunnablePopular(int pageNumber) {
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            try {

                Response response2 = getPopular(pageNumber).execute();

                if (cancelRequest) {
                    return;
                }

                if (response2.code() == 200) {

                    List<MovieModel> movieModels =
                            new ArrayList<>(((MovieSearchResponse)response2.body()).getMovieModel());

                    if (pageNumber == 1) {

                        mPopular.postValue(movieModels);
                    }
                    else {

                        List<MovieModel> currentMovies = mPopular.getValue();
                        currentMovies.addAll(movieModels);

                    }
                }
                else {

                    String error = response2.errorBody().string();
                    Log.d(TAG, "Error: " + error);
                    mPopular.postValue(null);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                mPopular.postValue(null);
            }

        }

        private Call<MovieSearchResponse> getPopular(int pageNumber) {

            return Service.getMovieApi().getPopular(Credentials.API_KEY, pageNumber);
        }

        private void setCancelRequest() {

            Log.d(TAG, "setCancelRequest: Canceling Search Request.");
            cancelRequest = true;
        }

    }

}