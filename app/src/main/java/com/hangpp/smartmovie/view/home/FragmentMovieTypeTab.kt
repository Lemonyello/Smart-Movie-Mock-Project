package com.hangpp.smartmovie.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hangpp.smartmovie.databinding.FragmentMovieTypeTabBinding
import com.hangpp.smartmovie.view.custom.RecyclerViewHome
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMovieTypeTab.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FragmentMovieTypeTab : Fragment() {

    private val viewModel by viewModels<TabMovieTypeViewModel>()
    private lateinit var tabType: TabType

    private lateinit var binding: FragmentMovieTypeTabBinding
    private lateinit var rvMovies: RecyclerViewHome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentMovieTypeTabBinding.inflate(LayoutInflater.from(context), container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
    }

    private fun setupUI() {
        tabType = arguments?.getSerializable("tabType") as TabType
        when (tabType) {
            TabType.Popular -> viewModel.fetchPopularMovies(1)
            TabType.TopRated -> viewModel.fetchTopRatedMovies(1)
            TabType.Upcoming -> viewModel.fetchUpcomingMovies(1)
            TabType.NowPlaying -> viewModel.fetchNowPlayingMovies(1)
        }

        viewModel.getMoviesLiked()

        rvMovies = binding.rvMovies
    }

    // TODO refresh
    private fun setupViewModel() {
        viewModel.moviesLiked.observe(viewLifecycleOwner) {
            rvMovies.setupObserver(viewModel.listMoviesOfType, it, viewLifecycleOwner)
        }

        rvMovies.apply {
            loadMoreMovies = { page ->
                when (tabType) {
                    TabType.Popular -> viewModel.fetchPopularMovies(page)
                    TabType.TopRated -> viewModel.fetchTopRatedMovies(page)
                    TabType.Upcoming -> viewModel.fetchUpcomingMovies(page)
                    TabType.NowPlaying -> viewModel.fetchNowPlayingMovies(page)
                }
            }

            insertFavoriteMovie = this@FragmentMovieTypeTab::insertFavoriteMovie
            deleteFavoriteMovie = this@FragmentMovieTypeTab::deleteFavoriteMovie
        }
    }

    private fun insertFavoriteMovie(movieId: String) = viewModel.insertFavoriteMovie(movieId)

    private fun deleteFavoriteMovie(movieId: String) = viewModel.deleteFavoriteMovie(movieId)

    // TODO some tabs have not been initialized
    fun changeListView() {
        try {
            rvMovies.changeListView()
        } catch (e: Exception) {
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(tabType: TabType) =
            FragmentMovieTypeTab().apply {
                arguments = bundleOf("tabType" to tabType)
            }
    }
}