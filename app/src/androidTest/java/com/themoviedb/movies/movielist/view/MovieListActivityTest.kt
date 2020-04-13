package com.themoviedb.movies.movielist.view

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.themoviedb.movies.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListActivityTest {

    var movieListActivity = IntentsTestRule(MovieListActivity::class.java)

    @Before
    fun startActivity(){
        movieListActivity.launchActivity(Intent())
    }

    @Test
    fun openSortByBottomSheetDialog(){
        /** is sort by option available */
        onView(withId(R.id.iv_sort_by)).check(matches(isDisplayed()))
        /** click on sort by option */
        onView(withId(R.id.iv_sort_by)).perform(ViewActions.click())
        /** is sort by bottom sheet dialog open */
        onView(withId(R.id.tv_sot_by)).check(matches(isDisplayed()))
    }
}