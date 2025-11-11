package com.example.cources1000

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cources1000.databinding.ActivityMainBinding
import com.example.feature_login.presentation.LoginNavigator

class MainActivity : AppCompatActivity(), LoginNavigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Обработка Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostFragment) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val isLogin = navController.currentDestination?.id == R.id.loginFragment

            val topPadding = if (isLogin) 0 else systemBars.top
            // NavHostFragment всегда занимает область между статусбаром и BottomNavigation
            v.setPadding(systemBars.left, topPadding, systemBars.right, 0)
            insets
        }

        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isLogin = destination.id == R.id.loginFragment
            binding.bottomNavigation.visibility = if (isLogin) View.GONE else View.VISIBLE

            // пересчитываем Insets для нового экрана
            binding.navHostFragment.requestApplyInsets()
        }
    }

    override fun navigateToHome() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.navigate(R.id.homeFragment)
    }
}
