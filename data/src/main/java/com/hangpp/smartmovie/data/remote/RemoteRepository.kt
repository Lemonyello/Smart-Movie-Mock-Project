package com.hangpp.smartmovie.data.remote

import com.hangpp.domain.model.*
import com.hangpp.domain.remote.IRemoteRepository
import com.hangpp.smartmovie.data.model.MovieResult
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.data.mapper.Mapper
import com.hangpp.smartmovie.data.mapper.MapperActorDtoToModel
import com.hangpp.smartmovie.data.mapper.MapperGenreDtoToModel
import com.hangpp.smartmovie.data.mapper.MapperMovieDtoToModel
import com.hangpp.smartmovie.data.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val service: MovieService,
    private val ioDispatcher: CoroutineDispatcher,
    private val mapperMovieDtoToModel: MapperMovieDtoToModel,
    private val mapperActorDtoToModel: MapperActorDtoToModel,
    private val mapperGenreDtoToModel: MapperGenreDtoToModel,
): IRemoteRepository {

    // Each Call from the MovieService can make a synchronous or asynchronous HTTP request to the server

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val response = service.getPopularMovies(page)

                returnMovies(response)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Result<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val response = service.getTopRatedMovies(page)

                returnMovies(response)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Result<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val response = service.getUpcomingMovies(page)

                returnMovies(response)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): Result<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val response = service.getNowPlayingMovies(page)

                returnMovies(response)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    override suspend fun getDetailMovie(movieId: String): Result<Movie> {
        return withContext(ioDispatcher) {
            try {
                val response = service.getDetailMovie(movieId)

                if (response.isSuccessful)
                    Result.Success(response.body()?.let { mapperMovieDtoToModel.mapFrom(it) })
                else
                    Result.Error(response.message(), null)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    override suspend fun getCastAndCrew(movieId: String): Result<List<Actor>> {
        return withContext(ioDispatcher) {
            try {
                val response = service.getCastAndCrewOfMovie(movieId)

                if (response.isSuccessful)
                    Result.Success(response.body()?.casts?.map{ actorDto -> mapperActorDtoToModel.mapFrom(actorDto) })
                else
                    Result.Error(response.message(), null) as Result<List<Actor>>
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    override suspend fun searchMovie(queryString: String): Result<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val response = service.searchMovie(queryString)

                returnMovies(response)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return withContext(ioDispatcher){
            try {
                val response = service.getGenres()

                if (response.isSuccessful)
                    Result.Success(response.body()?.genres?.map{ genreDto -> mapperGenreDtoToModel.mapFrom(genreDto)})
                else
                    Result.Error(response.message(), null)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    override suspend fun getMoviesOfGenre(genreId: String): Result<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val response = service.getMoviesOfGenre(genreId)

                returnMovies(response)
            } catch (e: Exception) {
                Result.Error(e.message.toString(), null)
            }
        }
    }

    private fun returnMovies(response: Response<MovieResult>)
    = if (response.isSuccessful)
        Result.Success(response.body()?.results?.map{ movieDto -> mapperMovieDtoToModel.mapFrom(movieDto)})
    else
        Result.Error(response.message(), null)
}