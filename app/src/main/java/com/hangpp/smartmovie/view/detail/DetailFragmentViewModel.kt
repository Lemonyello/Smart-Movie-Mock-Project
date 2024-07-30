package com.hangpp.smartmovie.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hangpp.domain.model.*
import com.hangpp.domain.remote.UseCaseGetCastAndCrew
import com.hangpp.domain.remote.UseCaseGetDetailMovie
import com.hangpp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFragmentViewModel @Inject constructor(
    private val useCaseGetDetailMovie: UseCaseGetDetailMovie,
    private val useCaseGetCastAndCrew: UseCaseGetCastAndCrew
) :
    ViewModel() {

    private var _movieDetail = MutableLiveData<Result<Movie>>()
    var movieDetail: LiveData<Result<Movie>> = _movieDetail

    private var _cast = MutableLiveData<Result<List<Actor>>>()
    var cast: LiveData<Result<List<Actor>>> = _cast

    fun fetchDetailMovie(movieId: String) {
        _movieDetail.postValue(Result.Loading)
        viewModelScope.launch {
            val result = useCaseGetDetailMovie(movieId)
            _movieDetail.postValue(result)
        }
    }

    fun fetchCastOfMovie(movieId: String) {
        _cast.postValue(Result.Loading)
        viewModelScope.launch {
            val result = useCaseGetCastAndCrew(movieId)
            _cast.postValue(result)
        }
    }
}