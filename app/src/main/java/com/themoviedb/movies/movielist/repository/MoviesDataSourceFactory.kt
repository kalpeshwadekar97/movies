package com.themoviedb.movies.movielist.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.themoviedb.movies.movielist.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSourceFactory(
    private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>()  {
    val moviesDataSourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val moviesDataSource =
            MoviesDataSource(
                compositeDisposable
            )
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }
}