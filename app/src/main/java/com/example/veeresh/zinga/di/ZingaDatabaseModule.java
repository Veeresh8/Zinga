package com.example.veeresh.zinga.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.veeresh.zinga.APIService;
import com.example.veeresh.zinga.LoginRespository;
import com.example.veeresh.zinga.MoviesRepositoryImpl;
import com.example.veeresh.zinga.ZingaDatabase;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

/**
 * Created by veeresh on 10/25/17.
 */

@Module (includes = NetworkModule.class)
public class ZingaDatabaseModule {


    @Provides
    @ZingaApplicationScope
    MoviesRepositoryImpl moviesRepository(ZingaDatabase zingaDatabase, APIService apiService) {
        return new MoviesRepositoryImpl(zingaDatabase, apiService);
    }

    @Provides
    @ZingaApplicationScope
    FirebaseAuth firebaseAuth(){
        return FirebaseAuth.getInstance();
    }


    @Provides
    @ZingaApplicationScope
    LoginRespository loginRespository(Context context, FirebaseAuth firebaseAuth){
        return new LoginRespository(firebaseAuth, context);
    }

    @Provides
    @ZingaApplicationScope
    ZingaDatabase zingaDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), ZingaDatabase.class, "Zinga_DB").build();
    }

    @Provides
    @ZingaApplicationScope
    ViewModelProvider.Factory provideViewModelFactory(MoviesRepositoryImpl moviesRepository, LoginRespository loginRespository){
        return new CustomViewModelFactory(moviesRepository, loginRespository);
    }
}
