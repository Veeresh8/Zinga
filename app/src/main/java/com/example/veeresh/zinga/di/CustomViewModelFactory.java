

package com.example.veeresh.zinga.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.veeresh.zinga.upcomingMovies.AlgoliaRepository;
import com.example.veeresh.zinga.upcomingMovies.AlgoliaViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final AlgoliaRepository moviesRepository;

    @Inject
    public CustomViewModelFactory(AlgoliaRepository moviesRepository) {
        this.moviesRepository = moviesRepository;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AlgoliaViewModel.class))
        return (T) new AlgoliaViewModel(moviesRepository);

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
