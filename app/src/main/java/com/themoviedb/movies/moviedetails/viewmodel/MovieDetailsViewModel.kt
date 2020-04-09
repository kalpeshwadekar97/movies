package com.themoviedb.movies.moviedetails.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.themoviedb.movies.api.ApiResponse
import com.themoviedb.movies.moviedetails.repository.MovieDetailsRepository
import com.themoviedb.movies.movielist.repository.MovieListRepository

class MovieDetailsViewModel(private val applicationContext: Application) :
    AndroidViewModel(applicationContext) {
    var movieDetailsRepository = MovieDetailsRepository()
    lateinit var movieDetailsResponseLiveData: MutableLiveData<ApiResponse>

    fun callMovieDetailsApi(url: String, api_key: String) {
        movieDetailsResponseLiveData =
            movieDetailsRepository.fetchMovieDetailsData(applicationContext, url, api_key)
    }
}