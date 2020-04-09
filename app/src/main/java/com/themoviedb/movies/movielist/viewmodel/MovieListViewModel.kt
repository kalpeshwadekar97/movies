package com.themoviedb.movies.movielist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.themoviedb.movies.api.ApiResponse
import com.themoviedb.movies.movielist.repository.MovieListRepository

class MovieListViewModel(private val applicationContext: Application) :
    AndroidViewModel(applicationContext) {

    var movieListRepository = MovieListRepository()
    lateinit var movieListResponseLiveData: MutableLiveData<ApiResponse>

    fun callMovieListApi(url: String, api_key: String) {
        movieListResponseLiveData =
            movieListRepository.fetchMovieListData(applicationContext, url, api_key)
    }
}