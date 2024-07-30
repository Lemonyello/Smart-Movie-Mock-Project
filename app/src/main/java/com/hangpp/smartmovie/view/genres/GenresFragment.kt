package com.hangpp.smartmovie.view.genres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hangpp.domain.model.Genre
import com.hangpp.smartmovie.R
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.databinding.FragmentGenresBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [GenresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class GenresFragment : Fragment() {

    private lateinit var binding: FragmentGenresBinding
    private lateinit var rvGenres: RecyclerView
    private val viewModel by viewModels<GenresViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGenresBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()

        viewModel.fetchGenres()
    }

    private fun setupViewModel() {
        viewModel.genres.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    // TODO loading genres
                }
                is Result.Success -> {
                    loadGenres(it.data!!)
                }
                is Result.Error -> {}
            }
        }
    }

    private fun setupView() {
        rvGenres = binding.rvGenres
        binding.toolbarGenres.inflateMenu(R.menu.toolbar_search)

    }

    private fun loadGenres(genres: List<Genre>) {
        rvGenres.adapter = GenresAdapter(genres)
        rvGenres.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance() = GenresFragment()
    }
}