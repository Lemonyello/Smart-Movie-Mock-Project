package com.hangpp.smartmovie.data.local

import com.hangpp.domain.local.ILocalRepository
import com.hangpp.domain.model.MovieLikedModel
import com.hangpp.smartmovie.data.mapper.Mapper
import com.hangpp.smartmovie.data.mapper.MapperMovieLikedDtoToModel
import com.hangpp.smartmovie.data.model.MovieLiked
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepository @Inject constructor(
    db: MyDatabase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mapperMovieLikedDtoToModel: MapperMovieLikedDtoToModel
) : ILocalRepository {

    private var movieDao: MovieDao = db.movieDao()

    override suspend fun insert(movieId: String) {
        withContext(ioDispatcher) {
            try {
                movieDao.insertAll(MovieLiked(0, movieId))
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun getByMovieId(movieId: String) =
        withContext(ioDispatcher) {
            try {
                movieDao.getByMovieId(movieId)?.let { mapperMovieLikedDtoToModel.mapFrom(it) }
            } catch (e: Exception) {
                null
            }
        }

    override suspend fun delete(movieId: String) {
        withContext(ioDispatcher) {
            try {
                movieDao.deleteByMovieId(movieId)
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun getAll() = withContext(ioDispatcher) {
        try {
            movieDao.getAll().map { mapperMovieLikedDtoToModel.mapFrom(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}