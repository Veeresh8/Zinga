

package com.example.veeresh.zinga.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.veeresh.zinga.LoginRespository;
import com.example.veeresh.zinga.LoginViewModel;
import com.example.veeresh.zinga.MoviesRepositoryImpl;
import com.example.veeresh.zinga.UpcomingMoviesViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final MoviesRepositoryImpl moviesRepository;
    private final LoginRespository loginRespository;

    @Inject
    public CustomViewModelFactory(MoviesRepositoryImpl moviesRepository, LoginRespository loginRespository) {
        this.moviesRepository = moviesRepository;
        this.loginRespository = loginRespository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UpcomingMoviesViewModel.class))
        return (T) new UpcomingMoviesViewModel(moviesRepository);

        else if (modelClass.isAssignableFrom(LoginViewModel.class))
        return (T) new LoginViewModel(loginRespository);
//
//        else if (modelClass.isAssignableFrom(NewListItemViewModel.class))
//        return (T) new NewListItemViewModel(repository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
