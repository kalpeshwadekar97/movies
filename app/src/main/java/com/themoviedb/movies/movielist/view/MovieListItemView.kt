package com.themoviedb.movies.movielist.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.themoviedb.movies.R
import com.themoviedb.movies.customviews.RecyclerViewRow
import com.themoviedb.movies.movielist.model.Movie
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieListItemView(context: Context, @Nullable attrs: AttributeSet) :
    ConstraintLayout(context, attrs), RecyclerViewRow<Movie> {
    override fun showData(item: Movie) {
        tv_movie_title.text = item.title
        tv_vote_count.text = context.getString(R.string.lbl_vote, item.vote_count)
        tv_vote_average.text = "${item.vote_average}"
        tv_release_date.text = context.getString(R.string.release_date, item.release_date)
        Glide.with(context).load("https://image.tmdb.org/t/p/w342/${item.poster_path}")
            .into(iv_movie)
    }
}