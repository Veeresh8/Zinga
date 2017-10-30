package com.example.veeresh.zinga;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by veeresh on 10/25/17.
 */

public class UpcomingMoviesViewModel extends ViewModel {


    private MoviesRepositoryImpl moviesRepository;

    @Inject
    public UpcomingMoviesViewModel(MoviesRepositoryImpl moviesRepository) {
        this.moviesRepository = moviesRepository;
    }


    public LiveData<ArrayList<Movies>> getMoviesLiveData(int page) {
        return moviesRepository.getMoviesLiveData(page);
    }


}
