package com.luongtran.cryptome.feature.coindetail.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.luongtran.cryptome.feature.coindetail.ui.CoinDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailRoute(val id: String, val name: String)

fun NavController.navigateToCoinDetail(
    id: String,
    name: String,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = CoinDetailRoute(id, name)) {
        navOptions()
    }
}

fun NavGraphBuilder.coinDetail() {
    composable<CoinDetailRoute> { entry ->
        val (id, name) = entry.toRoute<CoinDetailRoute>()
        CoinDetailScreen(id = id, name = name)
    }
}