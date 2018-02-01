package com.example.veeresh.zinga.network;

import com.example.veeresh.zinga.database.AlgoliaResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by veeresh on 10/24/17.
 */

public interface APIService {

    String API_KEY = "api_key";
    String PAGE = "page";

//    @GET("/3/movie/upcoming")
//    Observable<UpcomingMovies> getUpcomingMovies(@Query(API_KEY) String apiKey, @Query(PAGE) int page);

    @GET("api/v1/search")
    Observable<AlgoliaResponse> getAlgolia(@Query("query") String query);

}
