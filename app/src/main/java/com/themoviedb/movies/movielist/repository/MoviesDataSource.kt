package com.themoviedb.movies.movielist.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themoviedb.movies.api.ApiClient
import com.themoviedb.movies.api.ApiConstant
import com.themoviedb.movies.api.ApiService
import com.themoviedb.movies.enums.State
import com.themoviedb.movies.movielist.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviesDataSource(
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    var state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(
            ApiService.createService(ApiClient::class.java).getMovieList(
                ApiConstant.GET_MOVIE_LIST_API_URL,
                ApiConstant.API_KEY,
                1
            )
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(
                            response.results,
                            null,
                            2
                        )
                    },
                    {
                        updateState(State.ERROR)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            ApiService.createService(ApiClient::class.java).getMovieList(
                ApiConstant.GET_MOVIE_LIST_API_URL,
                ApiConstant.API_KEY,
                params.key
            )
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(
                            response.results,
                            params.key + 1
                        )
                    },
                    {
                        updateState(State.ERROR)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}
