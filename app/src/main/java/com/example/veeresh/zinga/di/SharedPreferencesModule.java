package com.example.veeresh.zinga.di;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by veeresh on 10/24/17.
 */

@Module(includes = ContextModule.class)
public class SharedPreferencesModule {

    @Provides
    @ZingaApplicationScope
    SharedPreferences sharedPreferences(Context context){
        return context.getSharedPreferences("Zinga_Preferences", Context.MODE_PRIVATE);
    }

}
