package com.luongtran.cryptome.feature.coindetail.ui.mapper

import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.common.model.Result
import com.luongtran.cryptome.core.common.model.StateLoading
import com.luongtran.cryptome.core.domain.CoinDetail
import com.luongtran.cryptome.core.domain.PriceHistory
import com.luongtran.cryptome.core.ui.mapper.getDrawableFor
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinDetailUiState
import com.luongtran.cryptome.feature.coindetail.ui.model.CoinPriceChartUiState

fun Result<CoinDetail>.toCoinDetailUiState(numberFormatter: NumberFormatter) = when (this) {
    is DataStateSuccess -> with(data) {
        CoinDetailUiState.Success(
            id = id,
            name = name,
            symbol = symbol,
            iconRes = getDrawableFor(symbol),
            priceUsd = DisplayableNumber(
                value = priceUsd,
                formatted = numberFormatter.formatPrice(priceUsd),
            ),
            marketCapUsd = DisplayableNumber(
                value = marketCapUsd,
                formatted = numberFormatter.formatPrice(marketCapUsd),
            ),
            rank = rank,
            volume24Hr = DisplayableNumber(
                value = volume24Hr,
                formatted = numberFormatter.formatPrice(volume24Hr),
            ),
            priceChange24h = DisplayableNumber(
                value = priceChange24h,
                formatted = numberFormatter.formatPercent(priceChange24h),
            ),
            allTimeHigh = DisplayableNumber(
                value = allTimeHigh,
                formatted = numberFormatter.formatPrice(allTimeHigh),
            ),
            allTimeLow = DisplayableNumber(
                value = allTimeLow,
                formatted = numberFormatter.formatPrice(allTimeLow),
            ),
            totalSupply = DisplayableNumber(
                value = totalSupply,
                formatted = numberFormatter.formatCryptoAmount(totalSupply, symbol),
            ),
            maxSupply = DisplayableNumber(
                value = maxSupply,
                formatted = numberFormatter.formatCryptoAmount(maxSupply, symbol),
            ),
            circulatingSupply = DisplayableNumber(
                value = circulatingSupply,
                formatted = numberFormatter.formatCryptoAmount(circulatingSupply, symbol),
            ),
        )
    }

    is DataStateError -> CoinDetailUiState.Error(exception.message.orEmpty())
    is StateLoading -> CoinDetailUiState.Loading
}

fun Result<PriceHistory>.toPriceChartUiState(numberFormatter: NumberFormatter) = when (this) {
    is DataStateError -> CoinPriceChartUiState.NotAvailable
    is StateLoading -> CoinPriceChartUiState.Loading
    is DataStateSuccess -> with(data) {
        when {
            prices.isEmpty() -> CoinPriceChartUiState.NotAvailable
            else -> CoinPriceChartUiState.Success(
                prices = prices,
                priceChangePercent = DisplayableNumber(
                    value = changePercent,
                    formatted = numberFormatter.formatPercent(changePercent * 100),
                ),
            )
        }
    }
}