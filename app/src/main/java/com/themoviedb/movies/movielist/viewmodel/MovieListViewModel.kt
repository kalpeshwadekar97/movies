package com.themoviedb.movies.movielist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themoviedb.movies.enums.State
import com.themoviedb.movies.movielist.model.Movie
import com.themoviedb.movies.movielist.repository.MoviesDataSource
import com.themoviedb.movies.movielist.repository.MoviesDataSourceFactory
import com.themoviedb.movies.utilities.AppConstants
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(private val applicationContext: Application) :
    AndroidViewModel(applicationContext) {

    var movieListResponseLiveData: LiveData<PagedList<Movie>>
    private val moviesDataSourceFactory: MoviesDataSourceFactory
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = AppConstants.PAGE_SIZE

    init {
        moviesDataSourceFactory =
            MoviesDataSourceFactory(
                compositeDisposable
            )
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(true)
            .build()
        movieListResponseLiveData = LivePagedListBuilder<Int, Movie>(moviesDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<MoviesDataSource,
            State>(moviesDataSourceFactory.moviesDataSourceLiveData, MoviesDataSource::state)

    fun listIsEmpty(): Boolean {
        return movieListResponseLiveData.value?.isEmpty() ?: true
    }
}