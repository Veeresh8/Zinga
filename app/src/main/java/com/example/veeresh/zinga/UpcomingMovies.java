package com.example.veeresh.zinga;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by veeresh on 10/25/17.
 */

public class UpcomingMovies {

    private ArrayList<Movies> results;

    public ArrayList<Movies> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movies> results) {
        this.results = results;
    }
}
