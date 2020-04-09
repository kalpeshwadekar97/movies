package com.themoviedb.movies.moviedetails.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.themoviedb.movies.api.ApiCallbackResponseListener
import com.themoviedb.movies.api.ApiManager
import com.themoviedb.movies.api.ApiResponse
import com.themoviedb.movies.utilities.NetworkManager

class MovieDetailsRepository {
    fun fetchMovieDetailsData(
        application: Application,
        url: String,
        api_key: String
    ): MutableLiveData<ApiResponse> {
        val movieDetailsResponseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
        if (!NetworkManager.isConnected(application)) {
            movieDetailsResponseLiveData.value = ApiResponse.noInternet()
        } else {
            movieDetailsResponseLiveData.value = ApiResponse.loading()
            ApiManager.fetchMoviesDetails(
                url,
                api_key,
                ApiCallbackResponseListener(movieDetailsResponseLiveData)
            )
        }
        return movieDetailsResponseLiveData
    }
}