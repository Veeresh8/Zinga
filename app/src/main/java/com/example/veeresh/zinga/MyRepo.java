package com.example.veeresh.zinga;

import android.content.Context;

import retrofit2.Retrofit;

/**
 * Created by veeresh on 10/25/17.
 */

public class MyRepo {

    private final Retrofit retrofit;
    private final Context context;

    MyRepo(Context context, Retrofit retrofit) {
        this.retrofit = retrofit;
        this.context = context;
    }
}
