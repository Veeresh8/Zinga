package com.example.veeresh.zinga.di;

import com.example.veeresh.zinga.MainActivity;

import dagger.Module;

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
