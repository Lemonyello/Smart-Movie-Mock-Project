package com.hangpp.domain.remote

import com.hangpp.domain.model.*
import com.hangpp.domain.util.Result

interface IRemoteRepository {

    suspend fun getPopularMovies(page: Int): Result<List<Movie>>

    suspend fun getTopRatedMovies(page: Int): Result<List<Movie>>

    suspend fun getUpcomingMovies(page: Int): Result<List<Movie>>

    suspend fun getNowPlayingMovies(page: Int): Result<List<Movie>>

    suspend fun getDetailMovie(movieId: String): Result<Movie>

    suspend fun getCastAndCrew(movieId: String): Result<List<Actor>>

    suspend fun searchMovie(queryString: String): Result<List<Movie>>

    suspend fun getGenres(): Result<List<Genre>>

    suspend fun getMoviesOfGenre(genreId: String): Result<List<Movie>>
}