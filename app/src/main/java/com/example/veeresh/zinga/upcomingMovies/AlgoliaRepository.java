package com.example.veeresh.zinga.upcomingMovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.veeresh.zinga.database.AlgoliaHits;
import com.example.veeresh.zinga.network.APIService;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by veeresh on 10/25/17.
 */

public class AlgoliaRepository {


    private APIService apiService;


    @Inject
    public AlgoliaRepository(APIService apiService) {
        this.apiService = apiService;
    }

    public LiveData<ArrayList<AlgoliaHits>> getAlgoliaHits(String query) {
        final MutableLiveData<ArrayList<AlgoliaHits>> data = new MutableLiveData<>();
        ArrayList<AlgoliaHits> hitsArrayList = new ArrayList<>();
        apiService.getAlgolia(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(algoliaResponse -> algoliaResponse.getHits())
                .subscribe(new Observer<AlgoliaHits>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AlgoliaHits algoliaHits) {
                        hitsArrayList.add(algoliaHits);

                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);

                    }

                    @Override
                    public void onComplete() {
                        data.setValue(hitsArrayList);

                    }
                });
        return data;
    }
}
