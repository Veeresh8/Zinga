package com.example.veeresh.zinga.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.veeresh.zinga.network.APIService;
import com.example.veeresh.zinga.upcomingMovies.AlgoliaRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by veeresh on 10/25/17.
 */

@Module (includes = NetworkModule.class)
public class ZingaDatabaseModule {


    @Provides
    @ZingaApplicationScope
    AlgoliaRepository moviesRepository(APIService apiService) {
        return new AlgoliaRepository(apiService);
    }

    @Provides
    @ZingaApplicationScope
    ViewModelProvider.Factory provideViewModelFactory(AlgoliaRepository moviesRepository){
        return new CustomViewModelFactory(moviesRepository);
    }
}
