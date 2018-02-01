package com.example.veeresh.zinga.database;

import java.util.List;

/**
 * Created by veeresh on 11/3/17.
 */

public class AlgoliaResponse {


    private List<AlgoliaHits> hits;

    public List<AlgoliaHits> getHits() {
        return hits;
    }

    public void setHits(List<AlgoliaHits> hits) {
        this.hits = hits;
    }
}
