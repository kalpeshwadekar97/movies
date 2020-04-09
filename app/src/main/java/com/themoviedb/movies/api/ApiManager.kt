package com.themoviedb.movies.api

import androidx.annotation.NonNull
import com.themoviedb.movies.moviedetails.model.MovieDetailsApiResponse
import com.themoviedb.movies.movielist.model.MovieListApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiManager {
    private lateinit var apiClient: ApiClient

    fun fetchMoviesList(url: String, api_key: String, listener: ApiCallbackResponse) {
        apiClient = ApiService.createService(ApiClient::class.java)
        val call = apiClient.getMovieList(url, api_key)
        call.enqueue(object : Callback<MovieListApiResponse> {
            override fun onResponse(@NonNull call: Call<MovieListApiResponse>, @NonNull response: Response<MovieListApiResponse>) {
                if (response.isSuccessful) {
                    listener.onSuccess(response.body())
                } else {
                    listener.onFail(ApiService.parseError(response).status_message)
                }
            }

            override fun onFailure(@NonNull call: Call<MovieListApiResponse>, @NonNull t: Throwable) {
                listener.onServerFail(t.message)
            }
        })
    }

    fun fetchMoviesDetails(url: String, api_key: String, listener: ApiCallbackResponse) {
        apiClient = ApiService.createService(ApiClient::class.java)
        val call = apiClient.getMovieDetails(url, api_key)
        call.enqueue(object : Callback<MovieDetailsApiResponse> {
            override fun onResponse(@NonNull call: Call<MovieDetailsApiResponse>, @NonNull response: Response<MovieDetailsApiResponse>) {
                if (response.isSuccessful) {
                    listener.onSuccess(response.body())
                } else {
                    listener.onFail(ApiService.parseError(response).status_message)
                }
            }

            override fun onFailure(@NonNull call: Call<MovieDetailsApiResponse>, @NonNull t: Throwable) {
                listener.onServerFail(t.message)
            }
        })
    }
}