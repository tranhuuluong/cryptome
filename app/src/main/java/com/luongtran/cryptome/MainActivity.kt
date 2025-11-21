package com.luongtran.cryptome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.luongtran.cryptome.core.designsystem.theme.CryptomeTheme
import com.luongtran.cryptome.feature.home.ui.navigation.HomeRoute
import com.luongtran.cryptome.feature.home.ui.navigation.homeScreen
import com.luongtran.cryptome.feature.search.ui.navigation.navigateToSearch
import com.luongtran.cryptome.feature.search.ui.navigation.searchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptomeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = ScaffoldDefaults.contentWindowInsets
                        .union(WindowInsets.ime)
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = HomeRoute,
                    ) {
                        homeScreen(onSearchBarClick = navController::navigateToSearch)
                        searchScreen(onBackClick = navController::navigateUp)
                    }
                }
            }
        }
    }
}