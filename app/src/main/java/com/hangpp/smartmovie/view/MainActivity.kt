package com.hangpp.smartmovie.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hangpp.smartmovie.R
//import com.hangpp.smartmovie.data.controller.RepeatingScheduler
import com.hangpp.smartmovie.databinding.ActivityMainBinding
import com.hangpp.smartmovie.view.home.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: Fragment
    private lateinit var navController: NavController

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_discover -> {
                    moveToScreen(R.id.navigation_home)
                    true
                }

                R.id.navigation_search -> {
                    moveToScreen(R.id.navigation_search)
                    true
                }

                R.id.navigation_genres -> {
                    moveToScreen(R.id.navigation_genres)
                    true
                }

                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_frameLy)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        setupCallbacks()

//        RepeatingScheduler.scheduleDailyNotification(this)
    }

    private fun initView() {
        val navigation: BottomNavigationView = binding.navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = (navHostFragment as NavHostFragment).navController
    }

    private fun setupCallbacks() {
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.popBackStack()
            }
        })
    }

    private fun moveToScreen(destination: Int) {
        navController.navigate(destination)
    }
}

