package com.example.veeresh.zinga;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by veeresh on 10/25/17.
 */

public class MoviesRepositoryImpl {


    private APIService apiService;

    private ZingaDatabase zingaDatabase;

    @Inject
    public MoviesRepositoryImpl(ZingaDatabase zingaDatabase, APIService apiService) {
        this.zingaDatabase = zingaDatabase;
        this.apiService = apiService;
    }

    public LiveData<ArrayList<Movies>> getMoviesLiveData(int page) {
        final MutableLiveData<ArrayList<Movies>> data = new MutableLiveData<>();
        ArrayList<Movies> moviesArrayList = new ArrayList<>();
        apiService.getUpcomingMovies(Config.API_KEY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<UpcomingMovies, Iterable<Movies>>() {
                    @Override
                    public Iterable<Movies> apply(UpcomingMovies upcomingMovies) throws Exception {
                        return upcomingMovies.getResults();
                    }
                })
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


}
