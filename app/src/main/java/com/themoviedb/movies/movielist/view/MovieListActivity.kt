package com.themoviedb.movies.movielist.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.themoviedb.movies.R
import com.themoviedb.movies.baseviews.BaseActivity
import com.themoviedb.movies.customviews.OnRecyclerViewItemClickListener
import com.themoviedb.movies.enums.State
import com.themoviedb.movies.moviedetails.view.MovieDetailsActivity
import com.themoviedb.movies.movielist.model.Movie
import com.themoviedb.movies.movielist.viewmodel.MovieListViewModel
import com.themoviedb.movies.utilities.AppConstants
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.toolbar_movies.*

class MovieListActivity : BaseActivity(), OnRecyclerViewItemClickListener<Movie> {

    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        setToolbar()
        movieListViewModel = ViewModelProvider(this)
            .get(MovieListViewModel::class.java)
        initAdapter()
        initState()
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbar.navigationIcon = null
    }

    private fun initAdapter() {
        movieListAdapter = MovieListAdapter(this, this)
        rv_movie_list.layoutManager = GridLayoutManager(this@MovieListActivity, 2)
        rv_movie_list.adapter = movieListAdapter
        movieListViewModel.movieListResponseLiveData.observe(this, Observer {
            movieListAdapter.submitList(it)
        })
    }

    private fun initState() {
        movieListViewModel.getState().observe(this, Observer { state ->
            if (!movieListViewModel.listIsEmpty()) {
                movieListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(this@MovieListActivity, MovieDetailsActivity::class.java)
        intent.putExtra(AppConstants.MOVIE_ID, movie.id)
        startActivity(intent)
    }
}
