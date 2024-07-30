package com.hangpp.smartmovie.view.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.hangpp.domain.util.Result
import com.hangpp.smartmovie.databinding.FragmentMainBinding
import com.hangpp.smartmovie.view.home.adapter.AdapterHomeTabs
import com.hangpp.smartmovie.view.common.tabNamesString
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hangpp.smartmovie.R
import dagger.hilt.android.AndroidEntryPoint
import android.R as AndroidR


enum class TabType { Popular, TopRated, Upcoming, NowPlaying }

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var vpHome: ViewPager2
    private lateinit var adapter: AdapterHomeTabs
    private lateinit var tabLayout: TabLayout
    private var dialog: AlertDialog? = null

    private val viewModel by viewModels<MainFragmentViewModel>({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListeners()
        setupViewModel()

        viewModel.apply {
            getMoviesLiked()
            fetchHomeMovies()
        }
    }

    private fun setupListeners() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "refresh",
            viewLifecycleOwner
        ) { _, _ -> viewModel.fetchHomeMovies() }

        requireActivity().supportFragmentManager.setFragmentResultListener(
            "moveToSeeAll",
            viewLifecycleOwner
        ) { _, data ->
            vpHome.currentItem = data.getInt("section")
        }
    }

    private fun setupTabs(tabNames: List<Int>) {
        adapter = AdapterHomeTabs(requireActivity(), tabNames)
        adapter.setupTabs()
        vpHome.adapter = adapter

        val successTabs = mutableListOf<String>()
        successTabs.add("Movies")
        successTabs.addAll(tabNamesString.filterIndexed { index, _ -> tabNames.contains(index) })

        TabLayoutMediator(tabLayout, vpHome) { tab, position ->
            tab.text = successTabs[position]
        }.attach()
    }

    private fun setupUI() {
        vpHome = binding.vpHome
        tabLayout = binding.tabLayout
        binding.toolbarMain.apply {
            inflateMenu(R.menu.toolbar_main)

            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.changeListView -> {
                        adapter.changeListView()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel.homeState.observe(viewLifecycleOwner) {
            when (it.loadState) {
                is Result.Success -> {
                    binding.ivProgressBar.visibility = View.INVISIBLE
                    tabLayout.visibility = View.VISIBLE
                    vpHome.visibility = View.VISIBLE
                    setupTabs(it.movieTabs)
                }

                is Result.Error -> {
                    showErrorDialog()
                    tabLayout.visibility = View.INVISIBLE
                    vpHome.visibility = View.INVISIBLE
                }

                is Result.Loading -> {
                    binding.ivProgressBar.visibility = View.VISIBLE
                    tabLayout.visibility = View.INVISIBLE
                    vpHome.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun showErrorDialog() {
        if (dialog == null) dialog = AlertDialog.Builder(context)
            .setTitle("Load data failed")
            .setMessage("Can't get data from server, please try again later.") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.

            .setPositiveButton(
                "Reload"
            ) { _, _ -> viewModel.fetchHomeMovies() }
            // A null listener allows the button to dismiss the dialog and take no further action.
//            .setNegativeButton(AndroidR.string.no, null)
            .setIcon(AndroidR.drawable.ic_dialog_alert)
            .show()
        else dialog!!.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}