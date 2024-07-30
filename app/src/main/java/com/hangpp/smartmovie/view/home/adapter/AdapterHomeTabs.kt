package com.hangpp.smartmovie.view.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hangpp.smartmovie.view.home.FragmentMovieTypeTab
import com.hangpp.smartmovie.view.home.FragmentMoviesTab
import com.hangpp.smartmovie.view.home.TabType

class AdapterHomeTabs(
    activity: FragmentActivity,
    private val tabNames: List<Int>,
) :
    FragmentStateAdapter(activity) {

    private lateinit var tabsMoviesType: List<FragmentMovieTypeTab>
    private lateinit var tabMovies: FragmentMoviesTab

    override fun getItemCount(): Int = tabNames.size + 1

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> tabMovies
        in 1.. tabNames.size -> tabsMoviesType[position - 1]
        else -> Fragment()
    }

    fun setupTabs() {
        tabMovies = FragmentMoviesTab.newInstance()
        tabsMoviesType = tabNames.map {
            when (it) {
                0 -> FragmentMovieTypeTab.newInstance(TabType.Popular)
                1 -> FragmentMovieTypeTab.newInstance(TabType.TopRated)
                2 -> FragmentMovieTypeTab.newInstance(TabType.Upcoming)
                3 -> FragmentMovieTypeTab.newInstance(TabType.NowPlaying)
                else -> FragmentMovieTypeTab.newInstance(TabType.Popular)
            }
        }
    }

    fun changeListView() {
        tabMovies.changeListView()
        tabsMoviesType.forEach {
            it.changeListView()
        }
    }
}

