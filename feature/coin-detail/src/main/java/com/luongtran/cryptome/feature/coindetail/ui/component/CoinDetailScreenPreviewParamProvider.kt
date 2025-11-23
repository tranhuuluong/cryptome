package com.luongtran.cryptome.feature.coindetail.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import com.luongtran.cryptome.core.ui.R as RUi

class CoinDetailScreenPreviewParamProvider : PreviewParameterProvider<CoinDetailUiState> {

    override val values: Sequence<CoinDetailUiState>
        get() = sequenceOf(
            CoinDetailScreenPreviewParamData.loading,
            CoinDetailScreenPreviewParamData.error,
            CoinDetailScreenPreviewParamData.success,
        )
}

object CoinDetailScreenPreviewParamData {
    val loading = CoinDetailUiState.Loading
    val error = CoinDetailUiState.Error(message = "Failed to load coin details.")
    val success = CoinDetailUiState.Success(
        id = "bitcoin",
        name = "Bitcoin",
        symbol = "BTC",
        iconRes = RUi.drawable.btc,
        rank = 1,
        marketCapUsd = DisplayableNumber(
            value = 600000000000,
            formatted = "$600,000,000,000"
        ),
        volume24Hr = DisplayableNumber(
            value = 35000000000,
            formatted = "$35,000,000,000"
        ),
        circulatingSupply = DisplayableNumber(
            value = 19000000,
            formatted = "19,000,000 BTC"
        ),
        totalSupply = DisplayableNumber(
            value = 21000000,
            formatted = "21,000,000 BTC"
        ),
        maxSupply = DisplayableNumber(
            value = 21000000,
            formatted = "21,000,000 BTC"
        ),
        allTimeHigh = DisplayableNumber(
            value = 69000,
            formatted = "$69,000"
        ),
        allTimeLow = DisplayableNumber(
            value = 67,
            formatted = "$67"
        ),
        priceUsd = DisplayableNumber(
            value = 32000,
            formatted = "$32,000"
        ),
        priceChange24h = DisplayableNumber(
            value = -2.5,
            formatted = "-2.5%"
        ),
    )
}