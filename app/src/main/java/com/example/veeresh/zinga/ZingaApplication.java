package com.example.veeresh.zinga;

import android.app.Application;

import com.example.veeresh.zinga.di.ContextModule;
import com.example.veeresh.zinga.di.DaggerZingaComponent;
import com.example.veeresh.zinga.di.ZingaComponent;

import timber.log.Timber;

/**
 * Created by veeresh on 10/24/17.
 */

public class ZingaApplication extends Application {

    private ZingaComponent zingaComponent;
    private static ZingaApplication instance;

    public synchronized static ZingaApplication getInstance(){
        if (instance == null){
            return new ZingaApplication();
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Timber.plant(new Timber.DebugTree());

        zingaComponent = DaggerZingaComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

    }

    public ZingaComponent getZingaComponent(){
        return zingaComponent;
    }
}
