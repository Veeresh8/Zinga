package com.example.veeresh.zinga;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.veeresh.zinga.database.Movies;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by veeresh on 10/31/17.
 */

public class SortedOptionsFragment extends Fragment implements MoviesSortedAdapter.FavoriteCheck {


    @BindView(R.id.rv_sorted_movies)
    RecyclerView recyclerView;
    @BindView(R.id.header)
    TextView header;
    Unbinder unbinder;
    private ArrayList<Movies> moviesArrayList = new ArrayList<>();
    private MoviesSortedAdapter adapter;
    private int sortType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sorted_options, parent, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        moviesArrayList = getArguments().getParcelableArrayList(MainActivity.MOVIES_LIST);
        sortType = getArguments().getInt(MainActivity.SORT_KEY);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MoviesSortedAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);

        if (sortType == 0)
            header.setText("Sorted By Most Recent");
        else if (sortType == 1)
            header.setText("Sorted By Title");
        else if (sortType == 2)
            header.setText("Sorted By Votes");
        else if (sortType == 3)
            header.setText("Sorted By Popularity");

        adapter.addMovies(moviesArrayList);
        adapter.changeSortType(sortType);


    }

    @OnClick(R.id.action_close)
    public void closeFragment() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void addToFavorites(Movies movies) {

    }

    @Override
    public void removeFromFavorites(Movies movies) {

    }
}
