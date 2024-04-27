package com.example.a5_marcin_fedorowicz.Home

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.a5_marcin_fedorowicz.R
import com.example.a5_marcin_fedorowicz.Settings.PhotoSwipeFragment
import com.example.a5_marcin_fedorowicz.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        applyTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dfContainer) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.startScreenFragment, R.id.listFragment, R.id.settingsFragment), binding.root)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.startScreenFragment -> supportActionBar?.title = "Home screen"
                R.id.listFragment -> supportActionBar?.title = "Shopping list"
                R.id.settingsFragment -> supportActionBar?.title = "Settings"
                R.id.addFragment -> supportActionBar?.title = "Add product"
                R.id.detailsFragment -> supportActionBar?.title = "Product details"
                R.id.modifyFragment -> supportActionBar?.title = "Modify product"
                R.id.photoSwipeFragment -> supportActionBar?.title = "Photo swipe"
                else -> supportActionBar?.title = "Default"
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun applyTheme() {
        val data : SharedPreferences = sharedPreferences
        val themeNum = data.getInt("theme_num", 0)
        when(themeNum) {
            1 -> {
                theme?.applyStyle(R.style.AppTheme3, true)
            }
            2 -> {
                theme?.applyStyle(R.style.AppTheme1, true)
            }
            3 -> {
                theme?.applyStyle(R.style.AppTheme2, true)
            }
            else -> {
                theme?.applyStyle(R.style.AppTheme3, true)
            }
        }
    }
}