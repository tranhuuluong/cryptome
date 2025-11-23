package com.luongtran.cryptome.feature.home.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.luongtran.cryptome.feature.home.ui.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object HomeScreen

fun NavGraphBuilder.homeScreen(
    onSearchBarClick: () -> Unit,
    onCoinClick: (String, String) -> Unit,
    coinDetailDestination: NavGraphBuilder.() -> Unit,
) {
    navigation<HomeRoute>(startDestination = HomeScreen) {
        composable<HomeScreen>() {
            HomeScreen(
                onSearchBarClick = onSearchBarClick,
                onCoinClick = onCoinClick,
            )
        }
        coinDetailDestination()
    }
}
