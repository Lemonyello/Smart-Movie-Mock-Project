package com.hangpp.domain.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseLocalModule {

    @Provides
    fun provideUseCaseGetAllMoviesLiked(localRepository: ILocalRepository): UseCaseGetAllMoviesLiked =
        UseCaseGetAllMoviesLiked(localRepository)

    @Provides
    fun provideUseCaseInsertMovieLiked(localRepository: ILocalRepository): UseCaseInsertMovieLiked =
        UseCaseInsertMovieLiked(localRepository)

    @Provides
    fun provideUseCaseDeleteMovieLiked(localRepository: ILocalRepository): UseCaseDeleteMovieLiked =
        UseCaseDeleteMovieLiked(localRepository)
}