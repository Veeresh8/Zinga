package com.example.veeresh.zinga.upcomingMovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.veeresh.zinga.MoviesAdapter;
import com.example.veeresh.zinga.R;
import com.example.veeresh.zinga.ZingaApplication;
import com.example.veeresh.zinga.database.Movies;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by veeresh on 11/2/17.
 */

public class UpcomingMoviesFragment extends Fragment implements MoviesAdapter.FavoriteCheck {


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @BindView(R.id.rv_upcoming)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private MoviesAdapter adapter;
    private UpcomingMoviesViewModel upcomingMoviesViewModel;
    private ArrayList<Movies> moviesList = new ArrayList<>();
    private FragmentDelegate fragmentDelegate;


    private static final int SORT_TYPE_TIMESTAMP = 0;
    private static final int SORT_TYPE_TITLE = 1;
    private static final int SORT_TYPE_VOTES = 2;
    private static final int SORT_TYPE_POPULARITY = 3;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentDelegate) {
            fragmentDelegate = (FragmentDelegate) context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_movies, parent, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initViewModel();
        initData();
        return view;
    }


    private void initData() {
        upcomingMoviesViewModel.getMoviesLiveData(1).observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movies> moviesArrayList) {
                if (moviesArrayList != null) {
                    moviesList.addAll(moviesArrayList);
                    adapter.addMovies(moviesArrayList);
                }
            }
        });

        upcomingMoviesViewModel.getFavoriteMovies().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                adapter.addFavoriteMovie(movies);
            }
        });



    }

    private void initViewModel() {
        ZingaApplication.getInstance().getZingaComponent().inject(this);
        upcomingMoviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel.class);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MoviesAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<Movies> getMovies() {
        return moviesList;
    }


    private void showSortOptions() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet_sort_options, null);
        bottomSheetDialog.setContentView(sheetView);

        RadioGroup radioGroup = sheetView.findViewById(R.id.rb_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_title:
                    fragmentDelegate.addFragment(SORT_TYPE_TITLE, moviesList);
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.rb_popularity:
                    fragmentDelegate.addFragment(SORT_TYPE_POPULARITY, moviesList);
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.rb_votes:
                    fragmentDelegate.addFragment(SORT_TYPE_VOTES, moviesList);
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.rb_recent:
                    fragmentDelegate.addFragment(SORT_TYPE_TIMESTAMP, moviesList);
                    bottomSheetDialog.dismiss();
                    break;

            }
        });
        bottomSheetDialog.show();


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface FragmentDelegate {
        void addFragment(int sortType, ArrayList<Movies> moviesArrayList);

        void addFavoriteFragment();
    }


    @OnClick(R.id.action_favorites)
    public void showFavorites() {
        fragmentDelegate.addFavoriteFragment();
    }

    @OnClick(R.id.action_sort)
    public void sortMovies() {
        showSortOptions();
    }
}
