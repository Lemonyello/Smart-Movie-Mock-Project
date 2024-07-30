package com.hangpp.smartmovie.view.custom

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hangpp.smartmovie.view.home.adapter.AdapterListMovie
import com.hangpp.smartmovie.view.home.adapter.ListType

open class RecyclerViewHomeCommon(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private var listType = ListType.List

    private fun configureGridLayout() {
        layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        (adapter as AdapterListMovie).apply {
            adapter = AdapterListMovie(
                ListType.Grid,
                this.movieList,
                this.movieLikedList,
                this.insertFavoriteMovie,
                this.deleteFavoriteMovie,
            )
        }
    }

    private fun configureListLayout() {
        layoutManager = LinearLayoutManager(context)

        (adapter as AdapterListMovie).apply {
            adapter = AdapterListMovie(
                ListType.List,
                this.movieList,
                this.movieLikedList,
                this.insertFavoriteMovie,
                this.deleteFavoriteMovie,
            )
        }
    }

    fun changeListView() {

        if (listType == ListType.Grid) {
            listType = ListType.List
            configureListLayout()
            return
        }
        if (listType == ListType.List) {
            listType = ListType.Grid
            configureGridLayout()
        }
    }
}