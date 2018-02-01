package com.example.veeresh.zinga.database;

import java.util.Comparator;

/**
 * Created by veeresh on 11/3/17.
 */

public class AlgoliaHits {


    private String title;
    private String author;
    private String url;


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlgoliaHits that = (AlgoliaHits) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    public static class AlgoHitsDefaultComparator implements Comparator<AlgoliaHits> {
        @Override
        public int compare(AlgoliaHits algoliaHits1, AlgoliaHits algoliaHits2) {
            return 0;
        }
    }


    public static final AlgoHitsDefaultComparator ALGO_HITS_DEFAULT_COMPARATOR = new AlgoHitsDefaultComparator();


}
