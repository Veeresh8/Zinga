package com.example.veeresh.zinga.di;

import android.content.Context;

import com.bumptech.glide.Glide;

import dagger.Module;
import dagger.Provides;

/**
 * Created by veeresh on 10/24/17.
 */

@Module (includes = ContextModule.class)
public class GlideModule {


    @Provides
    Glide glide(Context context){
        return Glide.get(context);
    }
}
