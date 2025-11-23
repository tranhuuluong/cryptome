package com.luongtran.cryptome.feature.coindetail.ui.model

import androidx.annotation.DrawableRes
import com.luongtran.cryptome.core.ui.model.DisplayableNumber

sealed interface CoinDetailUiState {
    data object Loading : CoinDetailUiState
    data class Error(val message: String) : CoinDetailUiState
    data class Success(
        val id: String,
        val name: String,
        val symbol: String,
        @param:DrawableRes val iconRes: Int,
        val priceUsd: DisplayableNumber,
        val marketCapUsd: DisplayableNumber,
        val rank: Int,
        val volume24Hr: DisplayableNumber,
        val priceChange24h: DisplayableNumber,
        val allTimeHigh: DisplayableNumber,
        val allTimeLow: DisplayableNumber,
        val totalSupply: DisplayableNumber,
        val maxSupply: DisplayableNumber,
        val circulatingSupply: DisplayableNumber,
        val priceHistory: List<Double>,
    ) : CoinDetailUiState
}