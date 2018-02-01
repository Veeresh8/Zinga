package com.example.veeresh.zinga;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.veeresh.zinga.database.AlgoliaHits;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by veeresh on 10/31/17.
 */


public abstract class AlgoliaAdapter extends RecyclerView.Adapter<AlgoliaAdapter.ViewHolder> {

    private ArrayList<AlgoliaHits> algoliaSortedList;
    private WebViewDelegate webViewDelegate;
    private android.os.Handler handler = new android.os.Handler();

    public AlgoliaAdapter(WebViewDelegate webViewDelegate, ArrayList<AlgoliaHits> algoliaSortedList) {
        this.webViewDelegate = webViewDelegate;
        this.algoliaSortedList = algoliaSortedList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if ((position >= getItemCount() - 1))
            loadMoreMessages();

        if (algoliaSortedList.get(position).getTitle() != null) {
            holder.hitsTitle.setText(algoliaSortedList.get(position).getTitle());
        }
        if (algoliaSortedList.get(position).getAuthor() != null) {
            holder.hitsAuthor.setText(algoliaSortedList.get(position).getAuthor());
        }
    }

    @Override
    public int getItemCount() {
        return algoliaSortedList.size();
    }


    public void addAlgoliaHits(List<AlgoliaHits> algoliaHits) {
        algoliaSortedList.addAll(algoliaHits);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, 2000);
    }

    public void clear() {
        algoliaSortedList.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView hitsTitle;
        @BindView(R.id.author)
        TextView hitsAuthor;
        @BindView(R.id.card_view)
        CardView rootView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rootView.setOnClickListener(v -> {
                if (algoliaSortedList.get(getAdapterPosition()).getUrl() != null) {
                    webViewDelegate.openWebView(algoliaSortedList.get(getAdapterPosition()).getUrl());
                }
            });
        }
    }


    private Comparator<AlgoliaHits> getComparator() {
        return AlgoliaHits.ALGO_HITS_DEFAULT_COMPARATOR;
    }

    public interface WebViewDelegate {
        void openWebView(String url);
    }

    public abstract void loadMoreMessages();


}
