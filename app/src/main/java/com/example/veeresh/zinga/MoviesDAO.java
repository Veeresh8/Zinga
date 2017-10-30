package com.example.veeresh.zinga;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.graphics.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by veeresh on 10/25/17.
 */

@Dao
public interface MoviesDAO {

    @Query("SELECT * FROM Movies")
    LiveData<List<Movies>> getMoviesList();

    @Query("SELECT * FROM Movies WHERE id =:id")
    LiveData<Movies> getMovie(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<Movies> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFavorites(Movies movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void removeFromFavorites(Movies movie);

}
