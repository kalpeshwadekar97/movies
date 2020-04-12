package com.themoviedb.movies.moviedetails.view

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.themoviedb.movies.R
import com.themoviedb.movies.utilities.AppConstants
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailsActivityTest {

    var movieDetailsActivity = IntentsTestRule(MovieDetailsActivity::class.java)

    @Before
    fun startActivity() {
        val intent = Intent()
        intent.putExtra(AppConstants.MOVIE_ID, 419704)
        movieDetailsActivity.launchActivity(intent)
    }

    @Test
    private fun addToWatchLater() {
        // check if floating action button is visible
        onView(withId(R.id.fab_watch_later)).check(matches(isDisplayed()))
        // perform click action on floating button
        onView(withId(R.id.fab_watch_later)).perform(ViewActions.click())
        // check if Snackbar appears with successful text
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.added_to_watch_later_list)))
    }

}