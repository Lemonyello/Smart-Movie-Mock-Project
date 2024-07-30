package com.hangpp.smartmovie.data.local

import android.database.Cursor
import androidx.room.*
import com.hangpp.smartmovie.data.model.MovieLiked

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieliked WHERE movieId LIKE :movieId LIMIT 1")
    suspend fun getByMovieId(movieId: String): MovieLiked?

    @Query("SELECT * FROM movieLiked")
    suspend fun getAll(): List<MovieLiked>

    @Query("SELECT * FROM movieLiked")
    fun getAllForProvider(): Cursor

    @Insert
    suspend fun insertAll(vararg movieLiked: MovieLiked)

    @Query("DELETE FROM movieLiked WHERE movieId = :movieId")
    suspend fun deleteByMovieId(movieId: String)
}