package com.hangpp.smartmovie.view.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hangpp.domain.model.Movie
import com.hangpp.smartmovie.R
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.databinding.FragmentGenreDetailBinding
import com.hangpp.smartmovie.view.home.adapter.AdapterListMovie
import com.hangpp.smartmovie.view.home.adapter.ListType
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [GenreDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class GenreDetailFragment : Fragment() {

    private lateinit var binding: FragmentGenreDetailBinding
    private lateinit var rvMoviesOfGenre: RecyclerView

    private val viewModel by viewModels<GenresViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGenreDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()

        val genreId = arguments?.getString("genreId")
        if (genreId != null) {
            viewModel.apply {
                fetchMoviesOfGenre(genreId)
                getMoviesLiked()
            }
        }
    }

    private fun setupViewModel() {
        viewModel.moviesOfGenre.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    renderMovies(it.data!!)
                }
                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    private fun setupView() {
        rvMoviesOfGenre = binding.rvMoviesOfGenre

        binding.toolbarGenreDetail.apply {
            inflateMenu(R.menu.toolbar_plain)
            binding.toolbarTitleGenreDetail.text = arguments?.getString("genreName")

            setNavigationIcon(R.drawable.arrow_left)
            setNavigationOnClickListener {
                (activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
                    .navController.navigateUp()
            }
        }
    }

    private fun renderMovies(movies: List<Movie>) {
        rvMoviesOfGenre.layoutManager = LinearLayoutManager(context)

        viewModel.moviesLiked.observe(viewLifecycleOwner) {
            rvMoviesOfGenre.adapter = AdapterListMovie(
                ListType.List,
                ArrayList(movies),
                it,
                ::insertFavoriteMovie,
                ::deleteFavoriteMovie
            )
        }
    }

    private fun insertFavoriteMovie(movieId: String) = viewModel.insertFavoriteMovie(movieId)

    private fun deleteFavoriteMovie(movieId: String) = viewModel.deleteFavoriteMovie(movieId)

    companion object {
        @JvmStatic
        fun newInstance() =
            GenreDetailFragment()
    }
}