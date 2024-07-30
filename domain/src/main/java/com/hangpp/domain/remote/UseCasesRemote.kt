package com.hangpp.domain.remote

import javax.inject.Inject

class UseCaseGetPopularMovies @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke(page: Int) = remoteRepository.getPopularMovies(page)
}

class UseCaseGetTopRatedMovies @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke(page: Int) = remoteRepository.getTopRatedMovies(page)
}

class UseCaseGetUpcomingMovies @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke(page: Int) = remoteRepository.getUpcomingMovies(page)
}

class UseCaseGetNowPlayingMovies @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke(page: Int) = remoteRepository.getNowPlayingMovies(page)
}

class UseCaseGetDetailMovie @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke(movieId: String) = remoteRepository.getDetailMovie(movieId)
}

class UseCaseGetCastAndCrew @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke(movieId: String) = remoteRepository.getCastAndCrew(movieId)
}

class UseCaseSearchMovie @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke(queryString: String) = remoteRepository.searchMovie(queryString)
}

class UseCaseGetGenres @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke() = remoteRepository.getGenres()
}

class UseCaseGetMoviesOfGenre @Inject constructor(private val remoteRepository: IRemoteRepository) {
    suspend operator fun invoke(genreId: String) = remoteRepository.getMoviesOfGenre(genreId)
}