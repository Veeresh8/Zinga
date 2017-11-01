

package com.example.veeresh.zinga.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.veeresh.zinga.upcomingMovies.MoviesRepository;
import com.example.veeresh.zinga.upcomingMovies.UpcomingMoviesViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final MoviesRepository moviesRepository;

    @Inject
    public CustomViewModelFactory(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UpcomingMoviesViewModel.class))
        return (T) new UpcomingMoviesViewModel(moviesRepository);

//        else if (modelClass.isAssignableFrom(LoginViewModel.class))
//        return (T) new LoginViewModel(loginRespository);
//
//        else if (modelClass.isAssignableFrom(NewListItemViewModel.class))
//        return (T) new NewListItemViewModel(repository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
