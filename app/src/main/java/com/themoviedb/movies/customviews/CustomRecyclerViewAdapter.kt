package com.themoviedb.movies.customviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerViewAdapter<T>(
    private val dataList: List<T>,
    private val onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener<T>?,
    private val layoutId: Int
) : RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val row = LayoutInflater.from(parent.context).inflate(layoutId, parent, false) as RecyclerViewRow<T>
        return ViewHolder(row)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.mRow.showData(dataList[position])
        (holder.mRow as View).setOnClickListener {
            onRecyclerViewItemClickListener?.onItemClick(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder<T>(var mRow: RecyclerViewRow<T>) : RecyclerView.ViewHolder(mRow as View)
}

interface RecyclerViewRow<T> {
    fun showData(item: T)
}

interface OnRecyclerViewItemClickListener<T> {
    fun onItemClick(model: T)
}
