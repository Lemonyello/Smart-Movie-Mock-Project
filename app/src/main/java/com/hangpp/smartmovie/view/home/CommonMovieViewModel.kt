package com.hangpp.smartmovie.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hangpp.domain.local.UseCaseDeleteMovieLiked
import com.hangpp.domain.local.UseCaseGetAllMoviesLiked
import com.hangpp.domain.local.UseCaseInsertMovieLiked
import com.hangpp.domain.model.MovieLikedModel
import com.hangpp.smartmovie.data.mapper.Mapper
import com.hangpp.smartmovie.data.model.MovieLiked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CommonMovieViewModel @Inject constructor(
    private val useCaseGetAllMoviesLiked: UseCaseGetAllMoviesLiked,
    private val useCaseInsertMovieLiked: UseCaseInsertMovieLiked,
    private val useCaseDeleteMovieLiked: UseCaseDeleteMovieLiked,
) : ViewModel() {

    private val _moviesLiked = MutableLiveData<List<MovieLikedModel>>()
    val moviesLiked: LiveData<List<MovieLikedModel>> = _moviesLiked

    fun getMoviesLiked() {
        viewModelScope.launch {
            val moviesLiked =
                useCaseGetAllMoviesLiked()
            _moviesLiked.postValue(moviesLiked)
        }
    }

    fun insertFavoriteMovie(movieId: String) {
        viewModelScope.launch {
            useCaseInsertMovieLiked(movieId)
        }
    }

    fun deleteFavoriteMovie(movieId: String) {
        viewModelScope.launch {
            useCaseDeleteMovieLiked(movieId)
        }
    }
}