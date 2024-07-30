package com.hangpp.domain.local

import com.hangpp.domain.model.MovieLikedModel

interface ILocalRepository {

    suspend fun insert(movieId: String)

    suspend fun getByMovieId(movieId: String): MovieLikedModel?

    suspend fun delete(movieId: String)

    suspend fun getAll(): List<MovieLikedModel>
}