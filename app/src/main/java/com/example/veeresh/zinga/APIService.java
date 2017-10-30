package com.example.veeresh.zinga;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by veeresh on 10/24/17.
 */

public interface APIService {

    String API_KEY = "api_key";
    String PAGE = "page";

    @GET("/3/movie/upcoming")
    Observable<UpcomingMovies> getUpcomingMovies(@Query(API_KEY) String apiKey, @Query(PAGE) int page);

}
