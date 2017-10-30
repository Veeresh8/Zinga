package com.example.veeresh.zinga.di;

import android.content.Context;

import com.example.veeresh.zinga.APIService;
import com.example.veeresh.zinga.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by veeresh on 10/24/17.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {

    private static final String BASE_URL = "https://api.themoviedb.org/";

    @Provides
    @ZingaApplicationScope
    APIService apiService(Retrofit retrofit) {
        return retrofit.create(APIService.class);
    }

    @Provides
    @ZingaApplicationScope
    File cacheFile(Context context) {
        return new File(context.getCacheDir(), "Zinga_Cache");
    }

    @Provides
    @ZingaApplicationScope
    Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); // 10MB
    }

    @Provides
    @ZingaApplicationScope
    HttpLoggingInterceptor loggingInterceptor() {

        HttpLoggingInterceptor interceptor;
        interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });

        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    @Provides
    @ZingaApplicationScope
    OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).cache(cache).build();
    }

    @Provides
    @ZingaApplicationScope
    Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @ZingaApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }
}
