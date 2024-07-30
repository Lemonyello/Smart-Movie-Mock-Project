package com.hangpp.smartmovie.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hangpp.domain.local.UseCaseDeleteMovieLiked
import com.hangpp.domain.local.UseCaseGetAllMoviesLiked
import com.hangpp.domain.local.UseCaseInsertMovieLiked
import com.hangpp.domain.model.Movie
import com.hangpp.domain.model.MovieLikedModel
import com.hangpp.domain.remote.UseCaseGetNowPlayingMovies
import com.hangpp.domain.remote.UseCaseGetPopularMovies
import com.hangpp.domain.remote.UseCaseGetTopRatedMovies
import com.hangpp.domain.remote.UseCaseGetUpcomingMovies
import com.hangpp.smartmovie.data.mapper.Mapper
import com.hangpp.smartmovie.data.model.MovieLiked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabMovieTypeViewModel @Inject constructor(
    private val useCaseGetPopularMovies: UseCaseGetPopularMovies,
    private val useCaseGetTopRatedMovies: UseCaseGetTopRatedMovies,
    private val useCaseGetUpcomingMovies: UseCaseGetUpcomingMovies,
    private val useCaseGetNowPlayingMovies: UseCaseGetNowPlayingMovies,
    useCaseGetAllMoviesLiked: UseCaseGetAllMoviesLiked,
    useCaseInsertMovieLiked: UseCaseInsertMovieLiked,
    useCaseDeleteMovieLiked: UseCaseDeleteMovieLiked,
) :
    CommonMovieViewModel(
        useCaseGetAllMoviesLiked,
        useCaseInsertMovieLiked,
        useCaseDeleteMovieLiked,
    ) {

    private val _listMoviesOfType = MutableLiveData<List<Movie>>()
    val listMoviesOfType: LiveData<List<Movie>> = _listMoviesOfType

    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            val response = useCaseGetPopularMovies(page)
            _listMoviesOfType.postValue(response.data ?: emptyList())
        }
    }

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch {
            val response = useCaseGetTopRatedMovies(page)
            _listMoviesOfType.postValue(response.data ?: emptyList())
        }
    }

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch {
            val response = useCaseGetUpcomingMovies(page)
            _listMoviesOfType.postValue(response.data ?: emptyList())
        }
    }

    fun fetchNowPlayingMovies(page: Int) {
        viewModelScope.launch {
            val response = useCaseGetNowPlayingMovies(page)
            _listMoviesOfType.postValue(response.data ?: emptyList())
        }
    }
}
