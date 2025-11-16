package com.luongtran.cryptome.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.luongtran.cryptome.feature.home.HomeScreen
import com.luongtran.cryptome.feature.home.model.FilterOption
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object HomeScreen

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) = navigate(route = HomeRoute, navOptions = navOptions)

fun NavGraphBuilder.homeScreen(
    onSearchBarClick: () -> Unit,
    onFilterChipClick: (FilterOption) -> Unit,
    onPurchasableCheckedChange: (Boolean) -> Unit,
) {
    navigation<HomeRoute>(startDestination = HomeScreen) {
        composable<HomeScreen>() {
            HomeScreen(
                onSearchBarClick = onSearchBarClick,
                onFilterChipClick = onFilterChipClick,
                onPurchasableCheckedChange = onPurchasableCheckedChange,
            )
        }
    }
}
