package com.themoviedb.movies.moviedetails.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.themoviedb.movies.customviews.RecyclerViewRow
import kotlinx.android.synthetic.main.item_genre_list.view.*

class GenreItemView(context: Context, @Nullable attrs: AttributeSet) :
    ConstraintLayout(context, attrs),
    RecyclerViewRow<String> {

    override fun showData(item: String) {
        tv_genre.text = item
    }
}