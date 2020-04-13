package com.themoviedb.movies.movielist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.themoviedb.movies.R
import com.themoviedb.movies.baseviews.BaseActivity
import com.themoviedb.movies.customviews.OnRecyclerViewItemClickListener
import com.themoviedb.movies.databinding.ActivityMovieListBinding
import com.themoviedb.movies.enums.SortByOptions
import com.themoviedb.movies.enums.State
import com.themoviedb.movies.moviedetails.view.MovieDetailsActivity
import com.themoviedb.movies.movielist.model.Movie
import com.themoviedb.movies.movielist.viewmodel.MovieListViewModel
import com.themoviedb.movies.utilities.AppConstants
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.toolbar_movies.*
import kotlinx.android.synthetic.main.toolbar_movies.view.*

class MovieListActivity : BaseActivity(), OnRecyclerViewItemClickListener<Movie> {

    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var binding: ActivityMovieListBinding
    private var sortedBy: SortByOptions = SortByOptions.POPULARITY // by default sort by popular
    private val initialPageNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MovieListActivity, R.layout.activity_movie_list)

        setToolbar()
        movieListViewModel = ViewModelProvider(this)
            .get(MovieListViewModel::class.java)
        movieListViewModel.getMovieList(initialPageNumber, sortedBy)
        setAdapter()
        initState()
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbar.navigationIcon = null
        toolbar.iv_sort_by.visibility = View.VISIBLE
        toolbar.iv_sort_by.setOnClickListener {
            onSortByClick()
        }
    }

    private fun setAdapter() {
        movieListAdapter = MovieListAdapter(this, this)
        rv_movie_list.apply {
            layoutManager = GridLayoutManager(this@MovieListActivity, 2)
            adapter = movieListAdapter
        }
        movieListViewModel.movieListResponseLiveData.observe(this, Observer {
            movieListAdapter.submitList(it)
        })
    }

    private fun initState() {
        movieListViewModel.getState().observe(this, Observer {
            rl_error.visibility = if (it == State.ERROR) View.VISIBLE else View.GONE
            if (!movieListViewModel.listIsEmpty()) {
                movieListAdapter.setState()
            }
        })
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(this@MovieListActivity, MovieDetailsActivity::class.java)
        intent.putExtra(AppConstants.MOVIE_ID, movie.id)
        startActivity(intent)
    }

    private fun onSortByClick() {
        val sortDialog = SortByBottomSheetDialogFragment(this, sortedBy)
        sortDialog.show(supportFragmentManager, "sort")
    }

    fun sortBy(selectedSortByOption: SortByOptions) {
        sortedBy = selectedSortByOption
        movieListViewModel.getMovieList(initialPageNumber, sortedBy)
        setAdapter()
    }

    fun onRetryClick() {
        movieListViewModel.retry()
    }
}
