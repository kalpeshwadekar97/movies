package com.themoviedb.movies.movielist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.themoviedb.movies.R
import com.themoviedb.movies.databinding.SortByOptionsBinding
import com.themoviedb.movies.enums.SortByOptions
import kotlinx.android.synthetic.main.sort_by_options.*

class SortByBottomSheetDialogFragment(context: Context, private val selectedSort: SortByOptions?) :
    BottomSheetDialogFragment() {
    private val mContext = context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SortByOptionsBinding =
            DataBindingUtil.inflate(inflater, R.layout.sort_by_options, container, false)
        binding.sortByDialogFragment = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when (selectedSort) {
            SortByOptions.POPULARITY -> tv_sort_by_popularity.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.green
                )
            )
            SortByOptions.RATINGS -> tv_sort_by_ratings.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.green
                )
            )
            SortByOptions.RELEASE_DATE -> tv_sort_by_release_date.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.green
                )
            )
            SortByOptions.VOTE_COUNT -> tv_sort_by_vote_count.setTextColor(
                ContextCompat.getColor(
                    mContext,
                    R.color.green
                )
            )
        }
    }

    fun onSortClick(selectedSortByOption: SortByOptions) {
        if (selectedSort != selectedSortByOption)
            (mContext as MovieListActivity).sortBy(selectedSortByOption)
        this.dismiss()
    }
}