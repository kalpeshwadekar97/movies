package com.themoviedb.movies.movielist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.themoviedb.movies.enums.SortByOptions
import com.themoviedb.movies.enums.State
import com.themoviedb.movies.movielist.model.Movie
import com.themoviedb.movies.movielist.repository.MoviesDataSource
import com.themoviedb.movies.movielist.repository.MoviesDataSourceFactory
import com.themoviedb.movies.utilities.AppConstants
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(private val applicationContext: Application) :
    AndroidViewModel(applicationContext) {

    lateinit var movieListResponseLiveData: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MoviesDataSourceFactory
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = AppConstants.PAGE_SIZE

    fun getMovieList(pageNumber: Int, sortByOptions: SortByOptions?) {
        moviesDataSourceFactory =
            MoviesDataSourceFactory(
                compositeDisposable,
                pageNumber,
                getSelectedSortByOption(sortByOptions)
            )
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(true)
            .build()
        movieListResponseLiveData =
            LivePagedListBuilder<Int, Movie>(moviesDataSourceFactory, config).build()
    }

    private fun getSelectedSortByOption(sortByOptions: SortByOptions?): String {
        return when (sortByOptions) {
            SortByOptions.POPULARITY -> AppConstants.SORT_BY_POPULARITY
            SortByOptions.RATINGS -> AppConstants.SORT_BY_RATINGS
            SortByOptions.RELEASE_DATE -> AppConstants.SORT_BY_RELEASE_DATE
            SortByOptions.VOTE_COUNT -> AppConstants.SORT_BY_VOTE_COUNT
            else -> ""
        }
    }

    fun getState(): LiveData<State> = Transformations.switchMap<MoviesDataSource,
            State>(moviesDataSourceFactory.moviesDataSourceLiveData, MoviesDataSource::state)

    fun listIsEmpty(): Boolean {
        return movieListResponseLiveData.value?.isEmpty() ?: true
    }
}