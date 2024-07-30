package com.hangpp.smartmovie.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("title") @Expose val title: String,
    @SerializedName("vote_average") @Expose val voteAverage: Float,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("release_date") @Expose val releaseDate: String,
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("genre_ids") @Expose val genreIds: List<String>,
)

data class MovieResult(
    @SerializedName("page") @Expose val page: Int,
    @SerializedName("results") @Expose val results: List<MovieDto>,
)

@Entity
data class MovieLiked(
    @PrimaryKey(autoGenerate = true) @SerializedName("id") @Expose val id: Int,
    @SerializedName("movieId") @Expose val movieId: String,
)