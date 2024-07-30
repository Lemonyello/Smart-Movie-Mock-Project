package com.hangpp.smartmovie.view.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hangpp.domain.local.UseCaseDeleteMovieLiked
import com.hangpp.domain.local.UseCaseGetAllMoviesLiked
import com.hangpp.domain.local.UseCaseInsertMovieLiked
import com.hangpp.domain.model.*
import com.hangpp.domain.remote.UseCaseGetGenres
import com.hangpp.domain.remote.UseCaseGetMoviesOfGenre
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.data.mapper.Mapper
import com.hangpp.smartmovie.data.model.MovieLiked
import com.hangpp.smartmovie.view.home.CommonMovieViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val useCaseGetGenres: UseCaseGetGenres,
    private val useCaseGetMoviesOfGenre: UseCaseGetMoviesOfGenre,
    useCaseGetAllMoviesLiked: UseCaseGetAllMoviesLiked,
    useCaseInsertMovieLiked: UseCaseInsertMovieLiked,
    useCaseDeleteMovieLiked: UseCaseDeleteMovieLiked,
) : CommonMovieViewModel(
    useCaseGetAllMoviesLiked,
    useCaseInsertMovieLiked,
    useCaseDeleteMovieLiked,
) {

    private val _genres = MutableLiveData<Result<List<Genre>>>()
    val genres: LiveData<Result<List<Genre>>> = _genres

    private val _moviesOfGenre = MutableLiveData<Result<List<Movie>>>()
    val moviesOfGenre: LiveData<Result<List<Movie>>> = _moviesOfGenre

    fun fetchGenres() {
        viewModelScope.launch {
            val result = useCaseGetGenres()
            _genres.postValue(result)
        }
    }

    fun fetchMoviesOfGenre(genreId: String) {
        viewModelScope.launch {
            val result = useCaseGetMoviesOfGenre(genreId)
            _moviesOfGenre.postValue(result)
        }
    }
}