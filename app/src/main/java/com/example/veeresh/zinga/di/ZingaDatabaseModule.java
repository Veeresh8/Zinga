package com.example.veeresh.zinga.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.veeresh.zinga.database.ZingaDatabase;
import com.example.veeresh.zinga.network.APIService;
import com.example.veeresh.zinga.upcomingMovies.MoviesRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by veeresh on 10/25/17.
 */

@Module (includes = NetworkModule.class)
public class ZingaDatabaseModule {


    @Provides
    @ZingaApplicationScope
    MoviesRepository moviesRepository(ZingaDatabase zingaDatabase, APIService apiService) {
        return new MoviesRepository(zingaDatabase, apiService);
    }

    @Provides
    @ZingaApplicationScope
    ZingaDatabase zingaDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), ZingaDatabase.class, "Zinga_DB").build();
    }

    @Provides
    @ZingaApplicationScope
    ViewModelProvider.Factory provideViewModelFactory(MoviesRepository moviesRepository){
        return new CustomViewModelFactory(moviesRepository);
    }
}
