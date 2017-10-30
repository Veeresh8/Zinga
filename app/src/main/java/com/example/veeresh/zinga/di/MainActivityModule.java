package com.example.veeresh.zinga.di;

import com.example.veeresh.zinga.MainActivity;
import com.example.veeresh.zinga.MyRepo;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by veeresh on 10/25/17.
 */

@Module (includes = NetworkModule.class)
public class MainActivityModule {

    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

}
