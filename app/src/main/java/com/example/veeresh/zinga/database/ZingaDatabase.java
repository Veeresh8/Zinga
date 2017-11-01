package com.example.veeresh.zinga.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by veeresh on 10/25/17.
 */

@Database(entities = { Movies.class }, version = 1)
public abstract class ZingaDatabase extends RoomDatabase {
    public abstract MoviesDAO moviesDAO();
}
