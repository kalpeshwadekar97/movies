package com.themoviedb.movies.movielist.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.themoviedb.movies.R
import com.themoviedb.movies.api.ApiConstant
import com.themoviedb.movies.api.ApiResponse
import com.themoviedb.movies.api.ApiResponseStatus
import com.themoviedb.movies.baseviews.BaseActivity
import com.themoviedb.movies.customviews.CustomRecyclerViewAdapter
import com.themoviedb.movies.customviews.EqualSpacingItemDecoration
import com.themoviedb.movies.customviews.OnRecyclerViewItemClickListener
import com.themoviedb.movies.enums.RecyclerViewDisplayMode
import com.themoviedb.movies.moviedetails.view.MovieDetailsActivity
import com.themoviedb.movies.movielist.model.Movie
import com.themoviedb.movies.movielist.model.MovieListApiResponse
import com.themoviedb.movies.movielist.viewmodel.MovieListViewModel
import com.themoviedb.movies.utilities.AppConstants
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.item_movie_list.view.*
import kotlinx.android.synthetic.main.toolbar_movies.*

class MovieListActivity : BaseActivity(), OnRecyclerViewItemClickListener<Movie> {

    private lateinit var movieListViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setToolbar()
        callMovieListApi()
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbar.navigationIcon = null
    }

    private fun callMovieListApi() {
        movieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        movieListViewModel.callMovieListApi("movie/popular", "dd30b848438f23d6eac5ff777b6d6e1f")
        movieListViewModel.movieListResponseLiveData.observe(this, Observer {
            processApiResponse(it, ApiConstant.GET_MOVIE_LIST_API)
        })
    }

    override fun processApiResponse(apiResponse: ApiResponse, api: String) {
        super.processApiResponse(apiResponse, api)
        when (api) {
            ApiConstant.GET_MOVIE_LIST_API ->
                if (apiResponse.status == ApiResponseStatus.SUCCESS) {
                    val movieListApiResponse = apiResponse.data as MovieListApiResponse
                    setMovieList(movieListApiResponse)
                }
        }
    }

    private fun setMovieList(movieListApiResponse: MovieListApiResponse) {
        rv_movie_list.apply {
            addItemDecoration(EqualSpacingItemDecoration(15, RecyclerViewDisplayMode.GRID))
            layoutManager = GridLayoutManager(this@MovieListActivity, 2)
            adapter = CustomRecyclerViewAdapter(
                movieListApiResponse.results,
                this@MovieListActivity,
                R.layout.item_movie_list
            )
        }
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(this@MovieListActivity, MovieDetailsActivity::class.java)
        intent.putExtra(AppConstants.MOVIE_ID, movie.id)
        startActivity(intent)
    }
}
