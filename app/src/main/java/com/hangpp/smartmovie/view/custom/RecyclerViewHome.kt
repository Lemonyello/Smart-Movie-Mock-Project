package com.hangpp.smartmovie.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hangpp.domain.model.Movie
import com.hangpp.domain.model.MovieLikedModel
import com.hangpp.smartmovie.data.model.MovieLiked
import com.hangpp.smartmovie.view.home.adapter.AdapterListMovie
import com.hangpp.smartmovie.view.home.adapter.ListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerViewHome(private var context: Context, private var attrs: AttributeSet) :
    RecyclerViewHomeCommon(context, attrs) {

    private var isLoading = false
    private var currentPage = 1
    var loadMoreMovies: (page: Int) -> Unit = {}
    var insertFavoriteMovie: (movieId: String) -> Unit = {}
    var deleteFavoriteMovie: (movieId: String) -> Unit = {}
    private var jobLoadMore: Job? = null

    init {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lyManager = layoutManager
                if (!isLoading && lyManager != null) {
                    if (lyManager is GridLayoutManager &&
                        lyManager.findLastCompletelyVisibleItemPosition() == adapter?.itemCount?.minus(
                            1
                        )
                    ) {
                        loadMore()
                        isLoading = true
                    }

                    if (lyManager is LinearLayoutManager &&
                        lyManager.findLastCompletelyVisibleItemPosition() == adapter?.itemCount?.minus(
                            1
                        )
                    ) {
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        currentPage++
        (adapter as AdapterListMovie).movieList.add(null)

        jobLoadMore?.cancel()
        jobLoadMore = MainScope().launch {
            withContext(Dispatchers.IO) { delay(2000) } // another dispatcher
            withContext(Dispatchers.Main) {// back to main
                (adapter as AdapterListMovie).movieList.removeAt(
                    adapter?.itemCount?.minus(1) ?: 0
                )

                loadMoreMovies(currentPage)
                isLoading = false
            }
        }
    }

    fun setupObserver(
        list: LiveData<List<Movie>>,
        movieLikedList: List<MovieLikedModel>,
        lifecycleOwner: LifecycleOwner
    ) {
        try {
            list.observe(lifecycleOwner) { movies ->
                if (currentPage == 1) {
                    layoutManager = LinearLayoutManager(context)

                    adapter = AdapterListMovie(
                        ListType.List,
                        ArrayList(movies),
                        movieLikedList,
                        insertFavoriteMovie,
                        deleteFavoriteMovie,
                    )
                } else {
                    (adapter as AdapterListMovie).movieList.addAll(movies)
                    scrollToPosition(adapter?.itemCount?.minus(22) ?: 0)
                }
            }
        } catch (e: Exception) {
        }
    }

    fun refresh() {
        currentPage = 1
    }
}
