package com.hangpp.domain.model

import androidx.recyclerview.widget.DiffUtil

data class Movie(
    val id: String,
    val title: String,
    val voteAverage: Float,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val backdropPath: String?,
    val genreIds: List<String>?,
) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}

data class MovieLikedModel(
    val id: Int,
    val movieId: String,
)