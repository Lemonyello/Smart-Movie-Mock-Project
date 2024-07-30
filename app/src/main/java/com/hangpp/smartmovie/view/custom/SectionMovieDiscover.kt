package com.hangpp.smartmovie.view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.hangpp.smartmovie.R
import com.hangpp.smartmovie.view.home.adapter.AdapterListMovie

class SectionMovieDiscover(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private var tvLabel: TextView
    private var rvMovies: RecyclerViewHomeCommon
    private var btnSeeAll: TextView

    init {
        val view = inflate(context, R.layout.section_movie_discover, this)
        view.apply {
            tvLabel = findViewById(R.id.lb)
            rvMovies = findViewById(R.id.rvMovies)
            btnSeeAll = findViewById(R.id.btnViewAll)
        }

        rvMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun bind(
        label: String,
        adapter: AdapterListMovie,
        moveToSectionAll: (Int) -> Unit,
        section: Int
    ) {
        tvLabel.text = label
        rvMovies.adapter = adapter
        btnSeeAll.setOnClickListener {
            moveToSectionAll(section)
        }
    }

    fun changeListView() {
        rvMovies.changeListView()
    }
}