package com.example.veeresh.zinga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.veeresh.zinga.database.Movies;
import com.example.veeresh.zinga.upcomingMovies.FavoritesFragment;
import com.example.veeresh.zinga.upcomingMovies.UpcomingMoviesFragment;
import com.example.veeresh.zinga.utitlities.ActivityUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UpcomingMoviesFragment.FragmentDelegate {


    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    public static final String SORT_KEY = "sort_key";
    public static final String MOVIES_LIST = "movies_list";
    public static final String SORTED_FRAGMENT = "sorted_fragment";
    public static final String FAVORITE_FRAGMENT = "favorite_fragment";

    private UpcomingMoviesFragment upcomingMoviesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        if (upcomingMoviesFragment == null) {
            upcomingMoviesFragment = new UpcomingMoviesFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), upcomingMoviesFragment, R.id.fragment_container);
        }
    }

    @Override
    public void addFragment(int sortType, ArrayList<Movies> moviesArrayList) {
        Bundle bundle = new Bundle();
        bundle.putInt(SORT_KEY, sortType);
        bundle.putParcelableArrayList(MOVIES_LIST, moviesArrayList);

        SortedOptionsFragment sortedOptionsFragment = new SortedOptionsFragment();
        sortedOptionsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, sortedOptionsFragment, SORTED_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addFavoriteFragment() {
        FavoritesFragment favoritesFragment = new FavoritesFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, favoritesFragment, FAVORITE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}
