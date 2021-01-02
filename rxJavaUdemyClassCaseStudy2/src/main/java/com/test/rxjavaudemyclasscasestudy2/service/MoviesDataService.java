package com.test.rxjavaudemyclasscasestudy2.service;


import com.test.rxjavaudemyclasscasestudy2.model.MovieDBResponse;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDataService {

    @GET("movie/popular")
    Flowable<MovieDBResponse> getPopularMoviesWithRx(@Query("api_key") String apiKey);

}
