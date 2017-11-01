package com.example.veeresh.zinga;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.veeresh.zinga.database.Movies;
import com.example.veeresh.zinga.database.ZingaDatabase;
import com.example.veeresh.zinga.upcomingMovies.UpcomingMoviesViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, MoviesAdapter.FavoriteCheck {


    @BindView(R.id.rv_upcoming)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private MoviesAdapter adapter;
    public static final String SORT_KEY = "sort_key";

    private UpcomingMoviesViewModel upcomingMoviesViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    ZingaDatabase zingaDatabase;

    private static final int SORT_TYPE_TIMESTAMP = 0;
    private static final int SORT_TYPE_TITLE = 1;
    private static final int SORT_TYPE_VOTES = 2;
    private static final int SORT_TYPE_POPULARITY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initUI();

        ZingaApplication.getInstance().getZingaComponent().inject(this);

        upcomingMoviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpcomingMoviesViewModel.class);


        upcomingMoviesViewModel.getMoviesLiveData(1).observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movies> moviesArrayList) {
                adapter.addMovies(moviesArrayList);
            }
        });

    }

    private void initUI() {
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MoviesAdapter(this, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                showSortOptions();
                return true;
        }
        return true;
    }

    private void showSortOptions() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet_sort_options, null);
        bottomSheetDialog.setContentView(sheetView);

        RadioGroup radioGroup = sheetView.findViewById(R.id.rb_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_title:
                    addFragment(SORT_TYPE_TITLE);
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.rb_popularity:
                    addFragment(SORT_TYPE_POPULARITY);
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.rb_votes:
                    addFragment(SORT_TYPE_VOTES);
                    bottomSheetDialog.dismiss();
                    break;
                case R.id.rb_recent:
                    addFragment(SORT_TYPE_TIMESTAMP);
                    bottomSheetDialog.dismiss();
                    break;

            }
        });
        bottomSheetDialog.show();


    }

    private void addFragment(int sortKey) {
        Bundle bundle = new Bundle();
        bundle.putInt(SORT_KEY, sortKey);

        SortedOptionsFragment sortedOptionsFragment = new SortedOptionsFragment();
        sortedOptionsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, sortedOptionsFragment).addToBackStack(null).commit();
    }


    @Override
    public void addToFavorites(Movies movie) {
        upcomingMoviesViewModel.addToFavorites(movie);
    }

    @Override
    public void removeFromFavorites(Movies movie) {
        upcomingMoviesViewModel.removeFromFavorites(movie);
    }
}
