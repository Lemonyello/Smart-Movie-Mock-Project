package com.hangpp.smartmovie.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hangpp.domain.model.Movie
import com.hangpp.smartmovie.data.model.MovieDto
import com.hangpp.smartmovie.databinding.FragmentMoviesTabBinding
import com.hangpp.smartmovie.view.custom.SectionMovieDiscover
import com.hangpp.smartmovie.view.home.adapter.AdapterListMovie
import com.hangpp.smartmovie.view.home.adapter.ListType
import com.hangpp.smartmovie.view.common.tabNamesString
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMoviesTab.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class FragmentMoviesTab : Fragment() {

    private lateinit var binding: FragmentMoviesTabBinding

    private var tabNames: List<Int> = listOf()
    private var moviesLists: List<List<Movie>> = listOf()
    private var moveToSeeAll: (Int) -> Unit =
        {section -> requireActivity().supportFragmentManager.setFragmentResult("moveToSeeAll", bundleOf("section" to section)) }

    private lateinit var sectionPopular: SectionMovieDiscover
    private lateinit var sectionTopRated: SectionMovieDiscover
    private lateinit var sectionUpcoming: SectionMovieDiscover
    private lateinit var sectionNowPlaying: SectionMovieDiscover
    private var sections = listOf<SectionMovieDiscover>()
    private var tabNamesShow = listOf<String>()
    private var hiddenSections = listOf<SectionMovieDiscover>()
    private var showSections = listOf<SectionMovieDiscover>()

    private lateinit var pullToRefresh: SwipeRefreshLayout

    private val viewModel by viewModels<MainFragmentViewModel>({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesTabBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupSections()
        setupListener()
        setupViewModel()
    }

    private fun setupViewModel() {

        viewModel.moviesLiked.observe(viewLifecycleOwner) { moviesLiked ->
            showSections.forEachIndexed { index, section ->
                section.bind(
                    label = tabNamesShow[index],
                    adapter = AdapterListMovie(
                        ListType.List,
                        ArrayList(moviesLists[index]),
                        moviesLiked,
                        ::insertFavoriteMovie,
                        ::deleteFavoriteMovie,
                    ),
                    moveToSectionAll = moveToSeeAll,
                    section = index + 1
                )
            }
        }
    }

    private fun setupUI() {
        pullToRefresh = binding.pullToRefresh
        sectionPopular = binding.sectionPopular
        sectionTopRated = binding.sectionTopRated
        sectionUpcoming = binding.sectionUpcoming
        sectionNowPlaying = binding.sectionNowPlaying

        viewModel.homeState.value?.apply {
            tabNames = movieTabs
            moviesLists = movieLists.map { it.take(4) }
        }
    }


    private fun setupSections() {
        sections = listOf(
            sectionPopular,
            sectionTopRated,
            sectionUpcoming,
            sectionNowPlaying
        )

        tabNamesShow = tabNamesString.filterIndexed { index, _ -> tabNames.contains(index) }
        hiddenSections = sections.filterIndexed { index, _ -> !tabNames.contains(index) }
        showSections = sections.filterIndexed { index, _ -> tabNames.contains(index) }

        hiddenSections.forEach {
            it.visibility = View.GONE
        }
    }

    private fun setupListener() {
        pullToRefresh.setOnRefreshListener {
            setFragmentResult("refresh", bundleOf())
            pullToRefresh.isRefreshing = false
        }
    }

    private fun deleteFavoriteMovie(movieId: String) = viewModel.deleteFavoriteMovie(movieId)

    private fun insertFavoriteMovie(movieId: String) = viewModel.insertFavoriteMovie(movieId)


    fun changeListView() {
        showSections.forEach {
            it.changeListView()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentMoviesTab = FragmentMoviesTab()
    }
}