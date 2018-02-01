package com.example.veeresh.zinga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.veeresh.zinga.upcomingMovies.AlgoliaFragment;
import com.example.veeresh.zinga.utitlities.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;


    private AlgoliaFragment upcomingMoviesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        if (upcomingMoviesFragment == null) {
            upcomingMoviesFragment = new AlgoliaFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), upcomingMoviesFragment, R.id.fragment_container);
        }
    }


}
