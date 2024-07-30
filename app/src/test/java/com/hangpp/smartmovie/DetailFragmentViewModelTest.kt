package com.hangpp.smartmovie

import com.hangpp.domain.model.*
import com.hangpp.domain.remote.UseCaseGetCastAndCrew
import com.hangpp.domain.remote.UseCaseGetDetailMovie
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.view.detail.DetailFragmentViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailFragmentViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var viewModel: DetailFragmentViewModel

    private val useCaseGetDetailMovie: UseCaseGetDetailMovie = mockk(relaxed = true)

    private val useCaseGetCastAndCrew: UseCaseGetCastAndCrew = mockk(relaxed = true)

    private lateinit var movieDetailStates: MutableList<Result<Movie>>

    private lateinit var castStates: MutableList<Result<List<Actor>>>

    @Before
    fun setup() {
        viewModel = DetailFragmentViewModel(useCaseGetDetailMovie, useCaseGetCastAndCrew)
        movieDetailStates = mutableListOf()
        castStates = mutableListOf()
    }

    @Test
    fun `test success get movie detail`() {
        viewModel.movieDetail.observeForever {
            movieDetailStates.add(it)
        }

        coEvery { useCaseGetDetailMovie("1700") } returns Result.Success(mockk())

        viewModel.fetchDetailMovie("1700")

        assert(movieDetailStates.last() is Result.Success)
    }

    @Test
    fun `test success get cast of movie`() {
        viewModel.cast.observeForever {
            castStates.add(it)
        }

        coEvery { useCaseGetCastAndCrew("1700") } returns Result.Success(mockk())

        viewModel.fetchCastOfMovie("1700")

        assert(castStates.last() is Result.Success)
    }
}