package com.luongtran.cryptome.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.luongtran.cryptome.feature.search.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

@Serializable
data object SearchScreen

fun NavController.navigateToSearch(
    navOptions: NavOptions? = null
) {
    navigate(route = SearchRoute, navOptions = navOptions)
}

fun NavGraphBuilder.searchScreen() {
    navigation<SearchRoute>(startDestination = SearchScreen) {
        composable<SearchScreen>() {
            SearchScreen()
        }
    }
}
