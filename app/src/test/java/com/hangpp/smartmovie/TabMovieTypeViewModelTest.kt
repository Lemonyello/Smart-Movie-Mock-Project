package com.hangpp.smartmovie

import com.hangpp.domain.local.UseCaseDeleteMovieLiked
import com.hangpp.domain.local.UseCaseGetAllMoviesLiked
import com.hangpp.domain.local.UseCaseInsertMovieLiked
import com.hangpp.domain.remote.UseCaseGetNowPlayingMovies
import com.hangpp.domain.remote.UseCaseGetPopularMovies
import com.hangpp.domain.remote.UseCaseGetTopRatedMovies
import com.hangpp.domain.remote.UseCaseGetUpcomingMovies
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.view.home.TabMovieTypeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TabMovieTypeViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var viewModel: TabMovieTypeViewModel

    private val useCaseGetPopularMovies: UseCaseGetPopularMovies = mockk(relaxed = true)

    private val useCaseGetTopRatedMovies: UseCaseGetTopRatedMovies = mockk(relaxed = true)

    private val useCaseGetUpcomingMovies: UseCaseGetUpcomingMovies = mockk(relaxed = true)

    private val useCaseGetNowPlayingMovies: UseCaseGetNowPlayingMovies = mockk(relaxed = true)

    private val useCaseGetAllMoviesLiked: UseCaseGetAllMoviesLiked = mockk(relaxed = true)

    private val useCaseInsertMovieLiked: UseCaseInsertMovieLiked = mockk(relaxed = true)

    private val useCaseDeleteMovieLiked: UseCaseDeleteMovieLiked = mockk(relaxed = true)

    @Before
    fun setUp() {

        viewModel = TabMovieTypeViewModel(
            useCaseGetPopularMovies,
            useCaseGetTopRatedMovies,
            useCaseGetUpcomingMovies,
            useCaseGetNowPlayingMovies,
            useCaseGetAllMoviesLiked,
            useCaseInsertMovieLiked,
            useCaseDeleteMovieLiked
        )

        viewModel.listMoviesOfType.observeForever {}
    }

    @Test
    fun testSuccessGetPopularMovies() {
        coEvery { useCaseGetPopularMovies(1) } returns Result.Success(listOf(mockk()))

        viewModel.fetchPopularMovies(1)

        viewModel.listMoviesOfType.value?.isNotEmpty()?.let { assert(it) }
    }

    @Test
    fun testSuccessGetTopRatedMovies() {
        coEvery { useCaseGetTopRatedMovies(1) } returns Result.Success(listOf(mockk()))

        viewModel.fetchTopRatedMovies(1)

        viewModel.listMoviesOfType.value?.isNotEmpty()?.let { assert(it) }
    }

    @Test
    fun testSuccessGetUpcomingMovies() {
        coEvery { useCaseGetUpcomingMovies(1) } returns Result.Success(listOf(mockk()))

        viewModel.fetchUpcomingMovies(1)

        viewModel.listMoviesOfType.value?.isNotEmpty()?.let { assert(it) }
    }

    @Test
    fun testSuccessGetNowPlayingMovies() {
        coEvery { useCaseGetNowPlayingMovies(1) } returns Result.Success(listOf(mockk()))

        viewModel.fetchNowPlayingMovies(1)

        viewModel.listMoviesOfType.value?.isNotEmpty()?.let { assert(it) }
    }
}