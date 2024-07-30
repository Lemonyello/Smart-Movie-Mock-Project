package com.hangpp.smartmovie

import com.hangpp.domain.local.UseCaseDeleteMovieLiked
import com.hangpp.domain.local.UseCaseGetAllMoviesLiked
import com.hangpp.domain.local.UseCaseInsertMovieLiked
import com.hangpp.domain.remote.UseCaseGetGenres
import com.hangpp.domain.remote.UseCaseGetMoviesOfGenre
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.view.genres.GenresViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GenresViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var viewModel: GenresViewModel

    private val useCaseGetGenres: UseCaseGetGenres = mockk(relaxed = true)

    private val useCaseGetMoviesOfGenre: UseCaseGetMoviesOfGenre = mockk(relaxed = true)

    private val useCaseGetAllMoviesLiked: UseCaseGetAllMoviesLiked = mockk(relaxed = true)

    private val useCaseInsertMovieLiked: UseCaseInsertMovieLiked = mockk(relaxed = true)

    private val useCaseDeleteMovieLiked: UseCaseDeleteMovieLiked = mockk(relaxed = true)

    @Before
    fun setUp() {

        viewModel = GenresViewModel(
            useCaseGetGenres,
            useCaseGetMoviesOfGenre,
            useCaseGetAllMoviesLiked,
            useCaseInsertMovieLiked,
            useCaseDeleteMovieLiked
        )
    }

    @Test
    fun testSuccessGetGenresMovies() {
        coEvery { useCaseGetGenres() } returns Result.Success(listOf(mockk()))

        viewModel.fetchGenres()

        viewModel.genres.value?.let { assert(it is Result.Success) }
    }

    @Test
    fun testSuccessGetMoviesOfGenre() {
        coEvery { useCaseGetMoviesOfGenre("28") } returns Result.Success(listOf(mockk()))

        viewModel.fetchMoviesOfGenre("28")

        viewModel.moviesOfGenre.value?.let { assert(it is Result.Success) }
    }
}