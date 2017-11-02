package com.example.veeresh.zinga;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.veeresh.zinga.database.Movies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by veeresh on 10/31/17.
 */


public class MoviesSortedAdapter extends RecyclerView.Adapter<MoviesSortedAdapter.ViewHolder> {

    private SortedList<Movies> moviesSortedList;
    private Context context;
    private FavoriteCheck favoriteCheck;

    private int sortType = SORT_TYPE_DEFAULT;

    private static final int SORT_TYPE_TIMESTAMP = 0;
    private static final int SORT_TYPE_TITLE = 1;
    private static final int SORT_TYPE_VOTES = 2;
    private static final int SORT_TYPE_POPULARITY = 3;
    private static final int SORT_TYPE_DEFAULT = 4;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat expectedSimpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

    public MoviesSortedAdapter(Context context, FavoriteCheck favoriteCheck) {
        this.context = context;
        this.favoriteCheck = favoriteCheck;
        this.moviesSortedList = new SortedList<>(Movies.class, new SortedListAdapterCallback<Movies>(this) {
            @Override
            public int compare(Movies o1, Movies o2) {
                return getComparator().compare(o1, o2);
            }

            @Override
            public boolean areContentsTheSame(Movies oldItem, Movies newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areItemsTheSame(Movies item1, Movies item2) {
                return item1.getId() == item2.getId();
            }
        });
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.movieTitle.setText(moviesSortedList.get(position).getTitle());
        holder.movieReview.setText(moviesSortedList.get(position).getOverview());
        holder.movieVoteAverage.setText(String.valueOf(moviesSortedList.get(position).getVote_average()));
        holder.movieDate.setText(getDate(moviesSortedList.get(position).getRelease_date()));
        Glide.with(context).load(Movies.SIZE_POSTER + moviesSortedList.get(position).getPoster_path()).override(300, 200).into(holder.moviePoster);


        if (moviesSortedList.get(position).isFavorite()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return moviesSortedList.size();
    }


    public void addMoviesList(List<Movies> movies) {
        for (int i = 0; i < moviesSortedList.size(); i++) {
            if (!moviesSortedList.get(i).isFavorite()) {
                moviesSortedList.removeItemAt(i);
            }
        }
        moviesSortedList.addAll(movies);

    }

    public void addMovies(List<Movies> movies) {
        moviesSortedList.beginBatchedUpdates();
        moviesSortedList.addAll(movies);
        moviesSortedList.endBatchedUpdates();
    }

    public Movies getMovie(int position) {
        return moviesSortedList.get(position);
    }

    public void changeSortType(int sortType) {
        this.sortType = sortType;
        List<Movies> items = new ArrayList<>();
        for (int j = 0; j < moviesSortedList.size(); j++) {
            items.add(moviesSortedList.get(j));
        }
        moviesSortedList.clear();
        moviesSortedList.addAll(items);
        moviesSortedList.endBatchedUpdates();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView movieTitle;
        @BindView(R.id.review)
        TextView movieReview;
        @BindView(R.id.vote_average)
        TextView movieVoteAverage;
        @BindView(R.id.date)
        TextView movieDate;
        @BindView(R.id.poster)
        ImageView moviePoster;
        @BindView(R.id.cb_favorite)
        CheckBox checkBox;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            checkBox.setOnClickListener(v -> {
                if (checkBox.isChecked()) {
                    moviesSortedList.get(getAdapterPosition()).setFavorite(true);
                    favoriteCheck.addToFavorites(moviesSortedList.get(getAdapterPosition()));
                } else {
                    moviesSortedList.get(getAdapterPosition()).setFavorite(false);
                    favoriteCheck.removeFromFavorites(moviesSortedList.get(getAdapterPosition()));
                }
            });
        }
    }


    private Comparator<Movies> getComparator() {
        switch (sortType) {
            case SORT_TYPE_TITLE:
                return Movies.MOVIE_TITLE_COMPARATOR;
            case SORT_TYPE_VOTES:
                return Movies.MOVIE_VOTES_COMPARATOR;
            case SORT_TYPE_POPULARITY:
                return Movies.MOVIE_POPULARITY_COMPARATOR;
            case SORT_TYPE_TIMESTAMP:
                return Movies.MOVIE_TIMESTAMP_COMPARATOR;
            default:
                return Movies.MOVIE_DEFAULT_COMPARATOR;
        }
    }

    private String getDate(String date) {

        String expectedDate = null;

        try {
            Date tempDate = simpleDateFormat.parse(date);
            expectedDate = expectedSimpleDateFormat.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return expectedDate;
    }


    public interface FavoriteCheck {
        void addToFavorites(Movies movies);

        void removeFromFavorites(Movies movies);
    }

}
