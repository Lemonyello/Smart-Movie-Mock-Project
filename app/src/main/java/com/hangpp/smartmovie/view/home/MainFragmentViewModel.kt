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
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.data.mapper.Mapper
import com.hangpp.smartmovie.data.model.MovieLiked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    val useCaseGetPopularMovies: UseCaseGetPopularMovies,
    val useCaseGetTopRatedMovies: UseCaseGetTopRatedMovies,
    val useCaseGetUpcomingMovies: UseCaseGetUpcomingMovies,
    val useCaseGetNowPlayingMovies: UseCaseGetNowPlayingMovies,
    useCaseGetAllMoviesLiked: UseCaseGetAllMoviesLiked,
    useCaseInsertMovieLiked: UseCaseInsertMovieLiked,
    useCaseDeleteMovieLiked: UseCaseDeleteMovieLiked,
) :
    CommonMovieViewModel(
        useCaseGetAllMoviesLiked,
        useCaseInsertMovieLiked,
        useCaseDeleteMovieLiked,
    ) {

    private val _homeState = MutableLiveData(HomeState(Result.Loading, emptyList(), emptyList()))
    val homeState: LiveData<HomeState> = _homeState

    fun fetchHomeMovies() {
        _homeState.postValue(HomeState(Result.Loading, emptyList(), emptyList()))

        viewModelScope.launch {
            val responses = awaitAll(
                async { useCaseGetPopularMovies(1) },
                async { useCaseGetTopRatedMovies(1) },
                async { useCaseGetUpcomingMovies(1) },
                async { useCaseGetNowPlayingMovies(1) })

            if (responses.all { it is Result.Error }) _homeState.postValue(
                _homeState.value?.copy(
                    loadState = Result.Error()
                )
            )
            else {
                val successResponseIndices = mutableListOf<Int>()
                responses.forEachIndexed { index, res ->
                    if (res is Result.Success) successResponseIndices.add(index)
                }

                val successResponses = responses.filter { it is Result.Success }

                _homeState.postValue(
                    HomeState(
                        Result.Success(),
                        successResponses.map {
                            it.data ?: emptyList()
                        }, successResponseIndices
                    )
                )
            }
        }
    }
}

data class HomeState(
    val loadState: Result<Any>,
    val movieLists: List<List<Movie>>,
    val movieTabs: List<Int>
)