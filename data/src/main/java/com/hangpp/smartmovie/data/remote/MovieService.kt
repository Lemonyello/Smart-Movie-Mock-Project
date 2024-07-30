package com.hangpp.smartmovie.data.remote

import com.hangpp.smartmovie.data.model.ActorResult
import com.hangpp.smartmovie.data.model.GenreResult
import com.hangpp.smartmovie.data.model.MovieDto
import com.hangpp.smartmovie.data.model.MovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("/3/movie/popular?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieResult>

    @GET("/3/movie/top_rated?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getTopRatedMovies(@Query("page") page: Int): Response<MovieResult>

    @GET("/3/movie/upcoming?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getUpcomingMovies(@Query("page") page: Int): Response<MovieResult>

    @GET("/3/movie/now_playing?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): Response<MovieResult>

    @GET("/3/movie/{movieId}?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getDetailMovie(@Path("movieId") movieId: String): Response<MovieDto>

    @GET("/3/movie/{movieId}/credits?api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getCastAndCrewOfMovie(@Path("movieId") movieId: String) : Response<ActorResult>

    @GET("/3/search/movie?include_adult=false&api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun searchMovie(@Query("query") queryString: String): Response<MovieResult>

    @GET("/3/genre/movie/list?language=en&api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getGenres(): Response<GenreResult>

    @GET("/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&api_key=d5b97a6fad46348136d87b78895a0c06")
    suspend fun getMoviesOfGenre(@Query("with_genres") genreId: String): Response<MovieResult>
}