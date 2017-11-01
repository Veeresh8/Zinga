package com.example.veeresh.zinga.upcomingMovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.veeresh.zinga.Resource;
import com.example.veeresh.zinga.database.Movies;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by veeresh on 10/25/17.
 */

public class UpcomingMoviesViewModel extends ViewModel {


    private MoviesRepository moviesRepository;

    @Inject
    public UpcomingMoviesViewModel(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }


    public Completable addToFavorites(Movies movie) {
        return moviesRepository.addToFavorites(movie);
    }

    public Completable removeFromFavorites(Movies movie) {
        return moviesRepository.removeFromFavorites(movie);
    }

    public LiveData<ArrayList<Movies>> getMoviesLiveData(int page) {
        return moviesRepository.getMoviesLiveData(page);
    }

}
