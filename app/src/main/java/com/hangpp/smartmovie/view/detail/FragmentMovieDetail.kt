package com.hangpp.smartmovie.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.hangpp.smartmovie.R
import com.hangpp.smartmovie.databinding.FragmentMovieDetailBinding
import com.hangpp.smartmovie.view.ui.theme.Assignment2Theme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.graphics.Color as ColorCompose


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentMovieDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FragmentMovieDetail() : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel by viewModels<DetailFragmentViewModel>()
    private lateinit var movieId: String

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        return ComposeView(requireContext()).apply {
            setContent {
                Assignment2Theme {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = ColorCompose.White,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = {
                                    Text("Movie Detail")
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        (activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
                                            .navController.navigateUp()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.KeyboardArrowLeft,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            )
                        },
                    ) { innerPadding ->
                        FragmentDetail(viewModel, innerPadding)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        movieId = arguments?.getString("movieId") ?: ""

        viewModel.apply {
            fetchDetailMovie(movieId)
            fetchCastOfMovie(movieId)
        }
    }

    // TODO add back button
    private fun navigateBack() {
//        setNavigationIcon(R.drawable.arrow_left)
//        setNavigationOnClickListener {
//            (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
//                .navController.popBackStack()
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): FragmentMovieDetail = FragmentMovieDetail()
    }
}

// UI logic state holder
class ExpandableViewState(
    initState: Boolean = false
) {
    var isExpanded by mutableStateOf(initState)

    fun toggle() {
        if (!isExpanded) isExpanded = true
    }
}

//@Preview
@Composable
fun FragmentDetail(viewModel: DetailFragmentViewModel, innerPadding: PaddingValues) {

    val movieDetail by viewModel.movieDetail.observeAsState()
    val castList by viewModel.cast.observeAsState()
    var currentSectionIsCast by remember {
        mutableStateOf(true)
    }

    val expandState = remember {
        ExpandableViewState()
    }
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.value) {
        if (scrollState.canScrollBackward) expandState.isExpanded = false

        if (scrollState.value > 1130 && currentSectionIsCast) currentSectionIsCast = false

        if (scrollState.value < 700 && !currentSectionIsCast) currentSectionIsCast = true
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)) {
        movieDetail?.data?.let { movie ->
            ContentMovie(movie, expandState, currentSectionIsCast)
        }
        Column(
            modifier = Modifier
                .background(ColorCompose.LightGray)
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(horizontal = 10.dp)
        ) {
            castList?.data?.let { CastAndCrewSection(it) }
            movieDetail?.data?.let { SimilarMoviesSection(it) }
            Spacer(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
        }
    }
}





