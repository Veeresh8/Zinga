package com.example.veeresh.zinga.upcomingMovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.veeresh.zinga.database.AlgoliaHits;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by veeresh on 10/25/17.
 */

public class AlgoliaViewModel extends ViewModel {


    private AlgoliaRepository algoliaRepository;

    private final MutableLiveData<String> queryInput = new MutableLiveData();

    @Inject
    public AlgoliaViewModel(AlgoliaRepository algoliaRepository) {
        this.algoliaRepository = algoliaRepository;
    }

    public final LiveData<ArrayList<AlgoliaHits>> algoliaQuery =
            Transformations.switchMap(queryInput, query -> algoliaRepository.getAlgoliaHits(query));

    public void setQuery(String query) {
        queryInput.setValue(query);
    }
}
