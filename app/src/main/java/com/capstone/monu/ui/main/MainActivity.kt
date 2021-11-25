package com.capstone.monu.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.monu.R
import com.capstone.monu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfig = AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_food, R.id.navigation_daily).build()

        setupActionBarWithNavController(navController, appBarConfig)
        binding.navView.setupWithNavController(navController)
    }
}