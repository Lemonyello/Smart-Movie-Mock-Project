package com.hangpp.smartmovie.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hangpp.domain.model.Movie
import com.hangpp.smartmovie.R
import com.hangpp.smartmovie.data.model.MovieDto
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.databinding.FragmentSearchBinding
import com.hangpp.smartmovie.view.custom.CustomSearchView
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSearch.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FragmentSearch : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var rvSearchMovies: RecyclerView
    private lateinit var etSearch: CustomSearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupViewModel()
    }

    private fun setupUI() {
        rvSearchMovies = binding.rvSearchMovie
        rvSearchMovies.layoutManager = LinearLayoutManager(context)
        etSearch = binding.searchView
        etSearch.onEnterPressed = { searchWord -> viewModel.searchMovies(searchWord) }
        binding.toolbarSearch.inflateMenu(R.menu.toolbar_search)
    }

    private fun setupViewModel() {
        viewModel.searchMovies.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> it.data?.let { movieList -> renderList(movieList) }
                else -> {}
            }
        }
    }

    private fun renderList(movieList: List<Movie>) {
        rvSearchMovies.adapter = AdapterSearchMovie(movieList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment FragmentSearch.
         */
        @JvmStatic
        fun newInstance() = FragmentSearch()
    }
}