package com.themoviedb.movies.api

import com.themoviedb.movies.moviedetails.model.MovieDetailsApiResponse
import com.themoviedb.movies.movielist.model.MovieListApiResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    /**
     * Fetch movie list data
     */
    @GET("{url}")
    fun getMovieList(
        @Path(
            "url",
            encoded = true
        ) url: String, @Query("api_key") api_key: String, @Query("page") page_number: Int
    ): Single<MovieListApiResponse>

    /**
     * Fetch movie details
     */
    @GET("{url}")
    fun getMovieDetails(
        @Path(
            "url",
            encoded = true
        ) url: String, @Query("api_key") api_key: String
    ): Call<MovieDetailsApiResponse>
}