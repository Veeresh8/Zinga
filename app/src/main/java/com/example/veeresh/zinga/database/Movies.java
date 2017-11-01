package com.example.veeresh.zinga.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by veeresh on 10/25/17.
 */

@Entity
public class Movies {

    @PrimaryKey
    private long id;
    private long vote_count;
    private double vote_average;
    private String title;
    private double popularity;
    private String poster_path;
    private String overview;
    private String release_date;
    private long timestamp;
    private boolean isFavorite;

    public static final String SIZE_POSTER = "http://image.tmdb.org/t/p/w500/";


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public long getTimestamp() {

        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = simpleDateFormat.parse(getRelease_date());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        if (date != null) {
            return timestamp = date.getTime();
        }

        return 0;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "Movies{" +
                "title='" + title + '\'' +
                '}';
    }


    public static class MovieTitleComparator implements Comparator<Movies> {
        @Override
        public int compare(Movies first, Movies second) {
            return first.getTitle().compareToIgnoreCase(second.getTitle());
        }
    }

    public static class MovieVotesComparator implements Comparator<Movies> {
        @Override
        public int compare(Movies first, Movies second) {
            return Double.compare(second.getVote_average(), first.getVote_average());
        }
    }

    public static class MoviePopularityComparator implements Comparator<Movies> {
        @Override
        public int compare(Movies first, Movies second) {
            return Double.compare(first.getPopularity(), second.getPopularity());
        }
    }

    public static class MovieTimestampComparator implements Comparator<Movies> {
        @Override
        public int compare(Movies first, Movies second) {
            return Long.compare(second.getTimestamp(), first.getTimestamp());
        }
    }

    public static class MovieDefaultComparator implements Comparator<Movies> {
        @Override
        public int compare(Movies first, Movies second) {
            return 0;
        }
    }


    public static final MovieTitleComparator MOVIE_TITLE_COMPARATOR = new MovieTitleComparator();
    public static final MovieVotesComparator MOVIE_VOTES_COMPARATOR = new MovieVotesComparator();
    public static final MoviePopularityComparator MOVIE_POPULARITY_COMPARATOR = new MoviePopularityComparator();
    public static final MovieTimestampComparator MOVIE_TIMESTAMP_COMPARATOR = new MovieTimestampComparator();
    public static final MovieDefaultComparator MOVIE_DEFAULT_COMPARATOR = new MovieDefaultComparator();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movies movies = (Movies) o;

        if (id != movies.id) return false;
        if (vote_count != movies.vote_count) return false;
        if (Double.compare(movies.vote_average, vote_average) != 0) return false;
        if (Double.compare(movies.popularity, popularity) != 0) return false;
        if (timestamp != movies.timestamp) return false;
        if (isFavorite != movies.isFavorite) return false;
        if (title != null ? !title.equals(movies.title) : movies.title != null) return false;
        if (poster_path != null ? !poster_path.equals(movies.poster_path) : movies.poster_path != null)
            return false;
        if (overview != null ? !overview.equals(movies.overview) : movies.overview != null)
            return false;
        return release_date != null ? release_date.equals(movies.release_date) : movies.release_date == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (vote_count ^ (vote_count >>> 32));
        temp = Double.doubleToLongBits(vote_average);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        temp = Double.doubleToLongBits(popularity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (poster_path != null ? poster_path.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (isFavorite ? 1 : 0);
        return result;
    }
}
