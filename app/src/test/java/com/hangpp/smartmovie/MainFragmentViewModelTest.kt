package com.hangpp.smartmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hangpp.domain.local.*
import com.hangpp.domain.remote.*
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.view.home.HomeState
import com.hangpp.smartmovie.view.home.MainFragmentViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainFragmentViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var viewModel: MainFragmentViewModel

    private val useCaseGetPopularMovies: UseCaseGetPopularMovies = mockk(relaxed = true)

    private val useCaseGetTopRatedMovies: UseCaseGetTopRatedMovies = mockk(relaxed = true)

    private val useCaseGetUpcomingMovies: UseCaseGetUpcomingMovies = mockk(relaxed = true)

    private val useCaseGetNowPlayingMovies: UseCaseGetNowPlayingMovies = mockk(relaxed = true)

    private val useCaseGetAllMoviesLiked: UseCaseGetAllMoviesLiked = mockk(relaxed = true)

    private val useCaseInsertMovieLiked: UseCaseInsertMovieLiked = mockk(relaxed = true)

    private val useCaseDeleteMovieLiked: UseCaseDeleteMovieLiked = mockk(relaxed = true)

    private lateinit var homeStates: MutableList<HomeState>

    @Before
    fun setUp() {

        homeStates = mutableListOf()

        viewModel = MainFragmentViewModel(
            useCaseGetPopularMovies,
            useCaseGetTopRatedMovies,
            useCaseGetUpcomingMovies,
            useCaseGetNowPlayingMovies,
            useCaseGetAllMoviesLiked,
            useCaseInsertMovieLiked,
            useCaseDeleteMovieLiked
        )

        viewModel.homeState.observeForever {
            homeStates.add(it)
        }
    }

    @Test
    fun testSuccessGetAllMovies() {
        coEvery { useCaseGetPopularMovies(1) } returns Result.Success(listOf(mockk()))
        coEvery { useCaseGetTopRatedMovies(1) } returns Result.Success(listOf(mockk()))
        coEvery { useCaseGetNowPlayingMovies(1) } returns Result.Success(listOf(mockk()))
        coEvery { useCaseGetUpcomingMovies(1) } returns Result.Success(listOf(mockk()))

        viewModel.fetchHomeMovies()

        assert(homeStates.last().loadState is Result.Success)
    }
}