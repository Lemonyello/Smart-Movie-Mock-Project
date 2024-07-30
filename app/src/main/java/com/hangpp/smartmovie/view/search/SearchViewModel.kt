package com.hangpp.smartmovie.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hangpp.domain.model.Movie
import com.hangpp.domain.remote.UseCaseSearchMovie
import com.hangpp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCaseSearchMovie: UseCaseSearchMovie
) :
    ViewModel() {

    private val _searchMovies = MutableLiveData<Result<List<Movie>>>()
    val searchMovies: LiveData<Result<List<Movie>>> = _searchMovies

    fun searchMovies(queryString: String) {
        _searchMovies.postValue(Result.Loading)
        viewModelScope.launch {
            val response = useCaseSearchMovie(queryString)
            _searchMovies.postValue(response)
        }
    }
}