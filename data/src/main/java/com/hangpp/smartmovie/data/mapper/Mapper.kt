package com.hangpp.smartmovie.data.mapper

import com.hangpp.domain.model.*
import com.hangpp.smartmovie.data.model.*

interface Mapper<F, T> {
    fun mapFrom(from: F): T
}

class MapperMovieDtoToModel : Mapper<MovieDto, Movie> {
    override fun mapFrom(from: MovieDto): Movie = Movie(
        id = from.id,
        title = from.title,
        overview = from.overview,
        posterPath = from.posterPath,
        releaseDate = from.releaseDate,
        voteAverage = from.voteAverage,
        backdropPath = from.backdropPath,
        genreIds = from.genreIds,
    )
}

class MapperActorDtoToModel : Mapper<ActorDto, Actor> {
    override fun mapFrom(from: ActorDto): Actor = Actor(
        id = from.id,
        name = from.name,
        profilePath = from.profilePath,
    )
}

class MapperGenreDtoToModel : Mapper<GenreDto, Genre> {
    override fun mapFrom(from: GenreDto): Genre = Genre(
        id = from.id,
        name = from.name,
    )
}

class MapperMovieLikedDtoToModel : Mapper<MovieLiked, MovieLikedModel> {
    override fun mapFrom(from: MovieLiked): MovieLikedModel = MovieLikedModel(
        id = from.id,
        movieId = from.movieId
    )
}