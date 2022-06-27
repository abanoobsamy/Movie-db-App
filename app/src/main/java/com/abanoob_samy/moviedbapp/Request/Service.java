package com.abanoob_samy.moviedbapp.Request;

import com.abanoob_samy.moviedbapp.Utils.Credentials;
import com.abanoob_samy.moviedbapp.Utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit retrofit =
            new Retrofit.Builder().baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi() {
        return movieApi;
    }
}
