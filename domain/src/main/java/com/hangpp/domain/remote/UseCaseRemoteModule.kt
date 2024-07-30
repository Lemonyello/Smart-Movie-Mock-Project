package com.hangpp.domain.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseRemoteModule {

    @Provides
    fun provideUseCaseGetPopularMovies(remoteRepository: IRemoteRepository): UseCaseGetPopularMovies =
        UseCaseGetPopularMovies(remoteRepository)

    @Provides
    fun provideUseCaseGetTopRatedMovies(remoteRepository: IRemoteRepository): UseCaseGetTopRatedMovies =
        UseCaseGetTopRatedMovies(remoteRepository)

    @Provides
    fun provideUseCaseGetUpcomingMovies(remoteRepository: IRemoteRepository): UseCaseGetUpcomingMovies =
        UseCaseGetUpcomingMovies(remoteRepository)

    @Provides
    fun provideUseCaseGetNowPlayingMovies(remoteRepository: IRemoteRepository): UseCaseGetNowPlayingMovies =
        UseCaseGetNowPlayingMovies(remoteRepository)

    @Provides
    fun provideUseCaseGetDetailMovie(remoteRepository: IRemoteRepository): UseCaseGetDetailMovie =
        UseCaseGetDetailMovie(remoteRepository)

    @Provides
    fun provideUseCaseGetCastAndCrew(remoteRepository: IRemoteRepository): UseCaseGetCastAndCrew =
        UseCaseGetCastAndCrew(remoteRepository)

    @Provides
    fun provideUseCaseSearchMovie(remoteRepository: IRemoteRepository): UseCaseSearchMovie =
        UseCaseSearchMovie(remoteRepository)

    @Provides
    fun provideUseCaseGetGenres(remoteRepository: IRemoteRepository): UseCaseGetGenres =
        UseCaseGetGenres(remoteRepository)

    @Provides
    fun provideUseCaseGetMoviesOfGenre(remoteRepository: IRemoteRepository): UseCaseGetMoviesOfGenre =
        UseCaseGetMoviesOfGenre(remoteRepository)
}