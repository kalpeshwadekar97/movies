package com.themoviedb.movies.movielist.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.themoviedb.movies.api.ApiClient
import com.themoviedb.movies.api.ApiConstant
import com.themoviedb.movies.api.ApiService
import com.themoviedb.movies.enums.State
import com.themoviedb.movies.movielist.model.Movie
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers



class MoviesDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val pageNumber: Int,
    private val selectedSortByOption: String
) : PageKeyedDataSource<Int, Movie>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private val apiService = ApiService.createService(ApiClient::class.java)
    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(
            apiService.getMovieList(
                ApiConstant.GET_MOVIE_LIST_API_URL,
                ApiConstant.API_KEY,
                pageNumber,
                selectedSortByOption
            )
                .subscribe(
                    { response ->
                        updateState(State.DONE)
                        callback.onResult(
                            response.results,
                            null,
                            pageNumber + 1
                        )
                    },
                    {
                        updateState(State.ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            apiService.getMovieList(
                ApiConstant.GET_MOVIE_LIST_API_URL,
                ApiConstant.API_KEY,
                params.key,
                selectedSortByOption
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
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}
