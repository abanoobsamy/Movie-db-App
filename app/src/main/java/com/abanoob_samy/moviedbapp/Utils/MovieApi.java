package com.abanoob_samy.moviedbapp.Utils;

import com.abanoob_samy.moviedbapp.Models.MovieModel;
import com.abanoob_samy.moviedbapp.Response.MovieSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher

    // /search/movie?api_key={api_key}&query=
    @GET("/3/search/movie")
    Call<MovieSearchResponse> MOVIE_SEARCH_RESPONSE_CALL(@Query("api_key") String key
            , @Query("query") String query, @Query("page") int page);

    // for popular
    //becuase in popular i don't need query for search because i will choose directly for data.
    //https://api.themoviedb.org/3/movie/popular    ?api_key=0f8e0efa0b59ee992c39447f2d0b0188&page=1
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(@Query("api_key") String key,
                                         @Query("page") int page);

    // making search with id
    // https://api.themoviedb.org/3/movie/550?api_key=0f8e0efa0b59ee992c39447f2d0b0188
    //Remember that movie_id = 550 is for Fight Club
    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> MOVIE_MODEL_CALL(@Path("movie_id") int movie_id,
                                      @Query("api_key") String api_key);
}
