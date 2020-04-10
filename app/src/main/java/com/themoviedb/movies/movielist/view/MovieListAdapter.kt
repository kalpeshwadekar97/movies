package com.themoviedb.movies.movielist.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.themoviedb.movies.R
import com.themoviedb.movies.customviews.OnRecyclerViewItemClickListener
import com.themoviedb.movies.databinding.ItemMovieListBinding
import com.themoviedb.movies.movielist.model.Movie
import com.themoviedb.movies.utilities.AppConstants


class MovieListAdapter(
    private val context: Context,
    private val onMovieItemClick: OnRecyclerViewItemClickListener<Movie>
) : PagedListAdapter<Movie, MovieListAdapter.ViewHolder>(MOVIE_COMPARATOR) {

    class ViewHolder(itemView: ItemMovieListBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding: ItemMovieListBinding = itemView

        fun bind(
            context: Context,
            movie: Movie,
            clickListener: OnRecyclerViewItemClickListener<Movie>
        ) {
            binding.movie = movie
            Glide.with(context)
                .load("${AppConstants.IMAGE_PATH_W342}${movie.poster_path}")
                .placeholder(R.drawable.placeholder_w342)
                .into(binding.ivMovie)
            itemView.setOnClickListener {
                clickListener.onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.ViewHolder {
        val binding: ItemMovieListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie_list, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(context, movie, onMovieItemClick)
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    fun setState() {
        notifyItemChanged(super.getItemCount())
    }
}