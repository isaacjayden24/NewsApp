package com.project.newsapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        try {

            setContentView(R.layout.activity_main)





            bottomNavigationView = findViewById(R.id.bottom_nav)

            // Get NavHostFragment and NavController
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            // Setup bottom navigation with NavController
            bottomNavigationView.setupWithNavController(navController)

            // Hide BottomNav when in SplashScreenFragment
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.splashFragment) {
                    bottomNavigationView.visibility = View.GONE
                } else {

                    bottomNavigationView.visibility = View.VISIBLE
                }
            }




            Log.d("MainActivity", "Navigation setup successfully")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate", e)
            // Handle the error
        }
    }
}





