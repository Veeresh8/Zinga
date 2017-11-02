package com.example.veeresh.zinga.upcomingMovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.veeresh.zinga.MoviesSortedAdapter;
import com.example.veeresh.zinga.R;
import com.example.veeresh.zinga.ZingaApplication;
import com.example.veeresh.zinga.database.Movies;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by veeresh on 10/31/17.
 */

public class FavoritesFragment extends Fragment implements MoviesSortedAdapter.FavoriteCheck {


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @BindView(R.id.rv_sorted_movies)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private MoviesSortedAdapter adapter;
    private UpcomingMoviesViewModel upcomingMoviesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sorted_options, parent, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initViewModel();
        initFavorites();
        return view;
    }

    private void initFavorites() {

        upcomingMoviesViewModel.getFavoriteMovies().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                adapter.addMoviesList(movies);
            }
        });
    }

    private void initViewModel() {
        ZingaApplication.getInstance().getZingaComponent().inject(this);
        upcomingMoviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel.class);
    }


    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MoviesSortedAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void addToFavorites(Movies movie) {
        upcomingMoviesViewModel.addToFavorites(movie).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Successfully Added To Favorites");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("onError - Favorites: ", e);
                    }
                });
    }

    @Override
    public void removeFromFavorites(Movies movie) {
        upcomingMoviesViewModel.removeFromFavorites(movie).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Successfully Removed From Favorites");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("onError - Favorites: ", e);
                    }
                });
    }
}
