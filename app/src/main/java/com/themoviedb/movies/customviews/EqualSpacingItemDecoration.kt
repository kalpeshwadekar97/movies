package com.themoviedb.movies.customviews

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.movies.enums.RecyclerViewDisplayMode

class EqualSpacingItemDecoration(
    private val spacing: Int,
    private val displayMode: RecyclerViewDisplayMode = RecyclerViewDisplayMode.HORIZONTAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager
        setSpacingForDirection(outRect, layoutManager, position, itemCount)
    }

    private fun setSpacingForDirection(
        outRect: Rect,
        layoutManager: RecyclerView.LayoutManager?,
        position: Int,
        itemCount: Int
    ) {
        when (displayMode) {
            RecyclerViewDisplayMode.HORIZONTAL -> {
                outRect.left = spacing
                outRect.right = if (position == itemCount - 1) spacing else 0
                outRect.top = spacing
                outRect.bottom = spacing
            }
            RecyclerViewDisplayMode.VERTICAL -> {
                outRect.left = spacing
                outRect.right = spacing
                outRect.top = spacing
                outRect.bottom = if (position == itemCount - 1) spacing else 0
            }
            RecyclerViewDisplayMode.GRID -> if (layoutManager is GridLayoutManager) {
                val gridLayoutManager = layoutManager as GridLayoutManager?
                val cols = gridLayoutManager!!.spanCount
                var rows = itemCount / cols
                if (itemCount % 2 == 1) rows += 1

                outRect.left = if (position % cols == cols - 1) spacing / 2 else spacing
                outRect.right = if (position % cols == cols - 1) spacing else spacing / 2
                outRect.top = spacing
                outRect.bottom = spacing
            }
        }
    }
}