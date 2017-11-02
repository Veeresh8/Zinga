package com.example.veeresh.zinga.di;

import com.example.veeresh.zinga.MainActivity;
import com.example.veeresh.zinga.SortedOptionsFragment;
import com.example.veeresh.zinga.upcomingMovies.FavoritesFragment;
import com.example.veeresh.zinga.upcomingMovies.UpcomingMoviesFragment;
import com.example.veeresh.zinga.upcomingMovies.UpcomingMoviesViewModel;

import dagger.Component;

/**
 * Created by veeresh on 10/24/17.
 */

@ZingaApplicationScope
@Component(modules = {NetworkModule.class, SharedPreferencesModule.class, GlideModule.class, ZingaDatabaseModule.class})
public interface ZingaComponent {

    void inject(UpcomingMoviesViewModel upcomingMoviesViewModel);

    void inject(SortedOptionsFragment sortedOptionsFragment);

    void inject(MainActivity mainActivity);

    void inject(UpcomingMoviesFragment upcomingMoviesFragment);

    void inject(FavoritesFragment favoritesFragment);

}
