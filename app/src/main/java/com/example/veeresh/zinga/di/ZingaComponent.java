package com.example.veeresh.zinga.di;

import com.example.veeresh.zinga.MainActivity;
import com.example.veeresh.zinga.UpcomingMoviesViewModel;

import dagger.Component;

/**
 * Created by veeresh on 10/24/17.
 */

@ZingaApplicationScope
@Component(modules = { NetworkModule.class, SharedPreferencesModule.class, GlideModule.class, ZingaDatabaseModule.class })
public interface ZingaComponent {

    void inject(UpcomingMoviesViewModel upcomingMoviesViewModel);
    void inject(MainActivity mainActivity);

    interface Injectable {
        void inject(ZingaComponent zingaComponent);
    }
}
