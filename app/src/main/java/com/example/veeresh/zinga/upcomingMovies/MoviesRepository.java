package com.example.veeresh.zinga.upcomingMovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.veeresh.zinga.network.APIService;
import com.example.veeresh.zinga.network.Config;
import com.example.veeresh.zinga.database.Movies;
import com.example.veeresh.zinga.database.ZingaDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by veeresh on 10/25/17.
 */

public class MoviesRepository {


    private APIService apiService;

    private ZingaDatabase zingaDatabase;

    @Inject
    public MoviesRepository(ZingaDatabase zingaDatabase, APIService apiService) {
        this.zingaDatabase = zingaDatabase;
        this.apiService = apiService;
    }

    public LiveData<ArrayList<Movies>> getMoviesLiveData(int page) {

        final MutableLiveData<ArrayList<Movies>> data = new MutableLiveData<>();
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
        apiService.getUpcomingMovies(Config.API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(upcomingMovies -> upcomingMovies.getResults())
                .subscribe(new Observer<Movies>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Movies movies) {
                        moviesArrayList.add(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                        data.setValue(moviesArrayList);

                    }
                });
        return data;
    }


    public LiveData<List<Movies>> getFavoriteMovies() {
        return zingaDatabase.moviesDAO().getFavoriteMovies(true);
    }

    public Completable addToFavorites(Movies movie) {
        return Completable.fromAction(() -> zingaDatabase.moviesDAO().addToFavorites(movie));
    }

    public Completable removeFromFavorites(Movies movie) {
        return Completable.fromAction(() -> zingaDatabase.moviesDAO().removeFromFavorites(movie));
    }

}
