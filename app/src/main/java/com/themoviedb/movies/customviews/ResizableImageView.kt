package com.themoviedb.movies.customviews

import android.content.Context
import android.widget.ImageView
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View


class ResizableImageView : ImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val d = drawable

        if (d != null) {
            // ceil not round - avoid thin vertical gaps along the left/right edges
            val width = View.MeasureSpec.getSize(widthMeasureSpec)
            val height =
                Math.ceil((width.toFloat() * d.intrinsicHeight.toFloat() / d.intrinsicWidth.toFloat()).toDouble())
                    .toInt()
            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}