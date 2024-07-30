package com.hangpp.smartmovie

import com.hangpp.smartmovie.data.model.MovieDto
import com.hangpp.domain.remote.UseCaseSearchMovie
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.view.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var viewModel: SearchViewModel

    private val useCaseSearchMovie: UseCaseSearchMovie = mockk(relaxed = true)

    private lateinit var searchMoviesStates: MutableList<Result<List<MovieDto>>>

    @Before
    fun setUp() {
        viewModel = SearchViewModel(useCaseSearchMovie)

        searchMoviesStates = mutableListOf()

        viewModel.searchMovies.observeForever {
            searchMoviesStates.add(it)
        }
    }

    @Test
    fun `test success search movies`() {
        coEvery { useCaseSearchMovie("test") } returns Result.Success(listOf(mockk()))

        viewModel.searchMovies("test")

        assert(searchMoviesStates.last() is Result.Success)
    }
}