package com.themoviedb.movies.movielist.viewmodel

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.themoviedb.movies.enums.SortByOptions
import com.themoviedb.movies.utilities.AppConstants
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListViewModelTest {

    private lateinit var movieListViewModel : MovieListViewModel

    @Before
    fun createMovieViewModel(){
        movieListViewModel = MovieListViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun getSelectedSortByOption_forPopularity(){
        val result = movieListViewModel.getSelectedSortByOption(SortByOptions.POPULARITY)
        Assert.assertEquals(AppConstants.SORT_BY_POPULARITY,result)
    }

    @Test
    fun getSelectedSortByOption_forVoteCount(){
        val result = movieListViewModel.getSelectedSortByOption(SortByOptions.VOTE_COUNT)
        Assert.assertEquals(AppConstants.SORT_BY_VOTE_COUNT,result)
    }

    @Test
    fun getSelectedSortByOption_forReleaseDate(){
        val result = movieListViewModel.getSelectedSortByOption(SortByOptions.RELEASE_DATE)
        Assert.assertEquals(AppConstants.SORT_BY_RELEASE_DATE,result)
    }

    @Test
    fun getSelectedSortByOption_forRatings(){
        val result = movieListViewModel.getSelectedSortByOption(SortByOptions.RATINGS)
        Assert.assertEquals(AppConstants.SORT_BY_RATINGS,result)
    }

    @Test
    fun getSelectedSortByOption_forNull(){
        val result = movieListViewModel.getSelectedSortByOption(null)
        Assert.assertEquals("",result)
    }
}