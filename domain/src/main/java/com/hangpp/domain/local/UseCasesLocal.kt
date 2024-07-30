package com.hangpp.domain.local

import com.hangpp.domain.model.MovieLikedModel
import javax.inject.Inject

class UseCaseGetAllMoviesLiked @Inject constructor(private val localRepository: ILocalRepository) {
    suspend operator fun invoke(): List<MovieLikedModel> = localRepository.getAll()
}

class UseCaseInsertMovieLiked @Inject constructor(private val localRepository: ILocalRepository) {
    suspend operator fun invoke(movieId: String) = localRepository.insert(movieId)
}

class UseCaseDeleteMovieLiked @Inject constructor(private val localRepository: ILocalRepository) {
    suspend operator fun invoke(movieId: String) = localRepository.delete(movieId)
}