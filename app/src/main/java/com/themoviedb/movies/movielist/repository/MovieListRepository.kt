package com.themoviedb.movies.movielist.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.themoviedb.movies.api.ApiCallbackResponseListener
import com.themoviedb.movies.api.ApiManager
import com.themoviedb.movies.api.ApiResponse
import com.themoviedb.movies.utilities.NetworkManager

class MovieListRepository {
    fun fetchMovieListData(application: Application, url: String, api_key: String): MutableLiveData<ApiResponse> {
        val movieListResponseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
        if (!NetworkManager.isConnected(application)) {
            movieListResponseLiveData.value = ApiResponse.noInternet()
        } else {
            movieListResponseLiveData.value = ApiResponse.loading()
            ApiManager.fetchMoviesList(
                url,
                api_key,
                ApiCallbackResponseListener(movieListResponseLiveData)
            )
        }
        return movieListResponseLiveData
    }
}