package com.codetron.detail.data.services

import retrofit2.http.GET
import retrofit2.http.Path

interface DetailMovieFeatureApi {

    @GET("/api/v1/movie/{movie_id}")
    fun fetchDetailMovieById(@Path("movie_id") id: Int)

}