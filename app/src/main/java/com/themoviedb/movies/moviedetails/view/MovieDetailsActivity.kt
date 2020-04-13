package com.themoviedb.movies.moviedetails.view

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.themoviedb.movies.R
import com.themoviedb.movies.api.ApiConstant
import com.themoviedb.movies.api.ApiResponse
import com.themoviedb.movies.api.ApiResponseStatus
import com.themoviedb.movies.baseviews.BaseActivity
import com.themoviedb.movies.customviews.CustomRecyclerViewAdapter
import com.themoviedb.movies.customviews.EqualSpacingItemDecoration
import com.themoviedb.movies.databinding.ActivityMovieDetailsBinding
import com.themoviedb.movies.enums.RecyclerViewDisplayMode
import com.themoviedb.movies.moviedetails.model.MovieDetailsApiResponse
import com.themoviedb.movies.moviedetails.viewmodel.MovieDetailsViewModel
import com.themoviedb.movies.utilities.AppConstants
import com.themoviedb.movies.utilities.Helper
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : BaseActivity() {

    private var movieId: Int = -1
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var movieDetailsApiResponse: MovieDetailsApiResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this@MovieDetailsActivity,
            R.layout.activity_movie_details
        )
        binding.movieDetailsActivity = this

        setToolbar()
        manageIntentData()
        callMovieDetailsApi()
    }

    override fun setToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun manageIntentData() {
        movieId = intent.getIntExtra(AppConstants.MOVIE_ID, movieId)
    }

    private fun callMovieDetailsApi() {
        movieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        movieDetailsViewModel.callMovieDetailsApi(
            "${ApiConstant.GET_MOVIE_DETAILS_API_URL}$movieId",
            ApiConstant.API_KEY
        )
        movieDetailsViewModel.movieDetailsResponseLiveData.observe(this, Observer {
            processApiResponse(it, ApiConstant.GET_MOVIE_DETAILS_API)
        })
    }

    override fun processApiResponse(apiResponse: ApiResponse, api: String) {
        super.processApiResponse(apiResponse, api)
        when (api) {
            ApiConstant.GET_MOVIE_DETAILS_API ->
                if (apiResponse.status == ApiResponseStatus.SUCCESS) {
                    grp_content.visibility = View.VISIBLE
                    movieDetailsApiResponse = apiResponse.data as MovieDetailsApiResponse
                    setMovieDetailsPage()
                }
        }
    }

    private fun setMovieDetailsPage() {
        binding.movie = movieDetailsApiResponse
        toolbar.title = movieDetailsApiResponse.title
        Glide.with(this)
            .load("${AppConstants.IMAGE_PATH_W500}${movieDetailsApiResponse.poster_path}")
            .placeholder(R.drawable.placeholder_w500)
            .error(R.drawable.placeholder_w500)
            .into(iv_movie)
        tv_runtime.text = Helper.getFormattedTimeFromMinutes(movieDetailsApiResponse.runtime)
        tv_countries_languages.text = Helper.getFormattedCountryLanguageList(
            movieDetailsApiResponse.production_countries,
            movieDetailsApiResponse.spoken_languages
        )
        val genreList = getGenreList()
        rv_genres.apply {
            addItemDecoration(EqualSpacingItemDecoration(15, RecyclerViewDisplayMode.HORIZONTAL))
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            adapter =
                CustomRecyclerViewAdapter(
                    genreList,
                    null,
                    R.layout.item_genre_list
                )
        }
    }

    private fun getGenreList(): List<String> {
        val genreList = mutableListOf<String>()
        movieDetailsApiResponse.genres.forEach {
            genreList.add(it.name)
        }
        if (movieDetailsApiResponse.adult) genreList.add(getString(R.string.lbl_adult))
        return genreList
    }

    fun onWatchLaterClick() {
        showSnackBar(fab_watch_later, getString(R.string.added_to_watch_later_list))
    }
}