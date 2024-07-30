package com.hangpp.smartmovie.data.di

import android.content.Context
import androidx.room.Room
import com.hangpp.domain.local.ILocalRepository
import com.hangpp.domain.model.*
import com.hangpp.smartmovie.data.local.LocalRepository
import com.hangpp.smartmovie.data.local.MyDatabase
import com.hangpp.domain.remote.IRemoteRepository
import com.hangpp.smartmovie.data.mapper.*
import com.hangpp.smartmovie.data.model.*
import com.hangpp.smartmovie.data.remote.MovieService
import com.hangpp.smartmovie.data.remote.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideRemoteRepository(remoteRepository: RemoteRepository): IRemoteRepository =
        remoteRepository

    @Provides
    fun provideLocalRepository(localRepository: LocalRepository): ILocalRepository = localRepository
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideService(): MovieService {
        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        // the Retrofit class generate an implementation of MovieService interface
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

        val service: MovieService = retrofit.create(MovieService::class.java)

        return service
    }
}

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): MyDatabase =
        Room.databaseBuilder(context, MyDatabase::class.java, "database-name")
//            .fallbackToDestructiveMigration()
            .build()
}

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Provides
    fun provideMovieMapperDtoToModel(): MapperMovieDtoToModel = MapperMovieDtoToModel()

    @Provides
    fun provideActorMapperDtoToModel(): MapperActorDtoToModel = MapperActorDtoToModel()

    @Provides
    fun provideGenreMapperDtoToModel(): MapperGenreDtoToModel = MapperGenreDtoToModel()

    @Provides
    fun provideMovieLikedMapperDtoToModel(): MapperMovieLikedDtoToModel =
        MapperMovieLikedDtoToModel()
}
