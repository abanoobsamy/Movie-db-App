package com.abanoob_samy.moviedbapp;

import com.abanoob_samy.moviedbapp.Repositories.MovieRepositories;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors instance;

    private AppExecutors() {

    }

    public static AppExecutors getInstance() {

        if (instance == null) {

            instance = new AppExecutors();
        }
        return instance;
    }

    private final static ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getExecutorService() {
        return EXECUTOR_SERVICE;
    }
}
