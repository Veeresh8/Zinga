package com.example.veeresh.zinga.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by veeresh on 10/24/17.
 */
@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    Context context(){
        return context;
    }

}
