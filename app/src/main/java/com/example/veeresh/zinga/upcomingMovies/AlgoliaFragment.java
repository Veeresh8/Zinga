package com.example.veeresh.zinga.upcomingMovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.veeresh.zinga.AlgoliaAdapter;
import com.example.veeresh.zinga.R;
import com.example.veeresh.zinga.ZingaApplication;
import com.example.veeresh.zinga.database.AlgoliaHits;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by veeresh on 11/2/17.
 */

public class AlgoliaFragment extends Fragment implements AlgoliaAdapter.WebViewDelegate {


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @BindView(R.id.rv_upcoming)
    RecyclerView recyclerView;
    Unbinder unbinder;
    private AlgoliaAdapter adapter;
    private AlgoliaViewModel upcomingMoviesViewModel;
    @BindView(R.id.edt_input_query)
    EditText edtInputQuery;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    private ArrayList<AlgoliaHits> arrayList = new ArrayList<>();
    private ArrayList<AlgoliaHits> temparrayList = new ArrayList<>();


    private int startIndex = 0;
    private int endIndex = 5;

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_movies, parent, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initViewModel();
        initData();
        return view;
    }

    private void resetIndexes() {
        startIndex = 0;
        endIndex = 5;
    }


    private void initData() {
        toggleVisibility(true);
        upcomingMoviesViewModel.setQuery("");
        upcomingMoviesViewModel.algoliaQuery.observe(this, new Observer<ArrayList<AlgoliaHits>>() {
            @Override
            public void onChanged(@Nullable ArrayList<AlgoliaHits> algoliaHits) {
                if (algoliaHits != null) {
                    adapter.clear();
                    toggleVisibility(false);
                    temparrayList.clear();
                    temparrayList.addAll(algoliaHits);
                    addItems(algoliaHits);
                } else {
                    toggleVisibility(true);
                }
            }
        });
    }

    private void addItems(@Nullable ArrayList<AlgoliaHits> algoliaHits) {
        ArrayList<AlgoliaHits> hitsArrayList = handlePagination(algoliaHits, startIndex, endIndex);


        adapter.addAlgoliaHits(hitsArrayList);

    }

    private ArrayList<AlgoliaHits> handlePagination(ArrayList<AlgoliaHits> hits, int startIndex, int endIndex) {

        ArrayList<AlgoliaHits> algoliaHits = new ArrayList<>();


        for (int i = startIndex; i < endIndex; i++) {
            algoliaHits.add(hits.get(i));
        }


        return algoliaHits;
    }

    private void initViewModel() {
        ZingaApplication.getInstance().getZingaComponent().inject(this);
        upcomingMoviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(AlgoliaViewModel.class);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AlgoliaAdapter(this, arrayList) {
            @Override
            public void loadMoreMessages() {

                startIndex = endIndex;
                endIndex = endIndex + 5;

                if (endIndex <= temparrayList.size()) {
                    addItems(temparrayList);
                    Toast.makeText(getActivity(), "Loading More", Toast.LENGTH_SHORT).show();
                }
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.done)
    public void performSearch() {
        if (edtInputQuery.getText().toString().length() > 0) {
            toggleVisibility(true);
            upcomingMoviesViewModel.setQuery(edtInputQuery.getText().toString());
        }
    }


    private void toggleVisibility(boolean mustToggle) {
        if (mustToggle) {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void openWebView(String url) {
        openBrowser(getActivity(), url);
    }

    public void openBrowser(final Context context, String url) {
        if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
            url = HTTP + url;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(Intent.createChooser(intent, "Choose browser"));

    }
}
