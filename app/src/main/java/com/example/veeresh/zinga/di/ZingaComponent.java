package com.example.veeresh.zinga.di;

import com.example.veeresh.zinga.MainActivity;
import com.example.veeresh.zinga.upcomingMovies.AlgoliaFragment;
import com.example.veeresh.zinga.upcomingMovies.AlgoliaViewModel;

import dagger.Component;

/**
 * Created by veeresh on 10/24/17.
 */

@ZingaApplicationScope
@Component(modules = {NetworkModule.class, SharedPreferencesModule.class, GlideModule.class, ZingaDatabaseModule.class})
public interface ZingaComponent {

    void inject(AlgoliaViewModel upcomingMoviesViewModel);

    void inject(MainActivity mainActivity);

    void inject(AlgoliaFragment upcomingMoviesFragment);


}
