package com.luongtran.cryptome.feature.coindetail.ui.component

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinPriceChartUiState

class PriceChartPreviewParamProvider : PreviewParameterProvider<CoinPriceChartUiState> {
    override val values: Sequence<CoinPriceChartUiState>
        get() = sequenceOf(
            PriceChartPreviewParamData.loading,
            PriceChartPreviewParamData.notAvailable,
            PriceChartPreviewParamData.success,
        )
}

object PriceChartPreviewParamData {
    val loading = CoinPriceChartUiState.Loading
    val notAvailable = CoinPriceChartUiState.NotAvailable
    val success = CoinPriceChartUiState.Success(
        prices = listOf(100.0, 102.0, 101.0, 105.0, 110.0, 108.0, 112.0),
        priceChangePercent = DisplayableNumber(
            value = 12.0,
            formatted = "+12%",
        ),
    )
}