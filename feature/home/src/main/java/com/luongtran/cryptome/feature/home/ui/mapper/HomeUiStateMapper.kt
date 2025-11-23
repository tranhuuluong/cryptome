package com.luongtran.cryptome.feature.home.ui.mapper

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.mapper.getDrawableFor
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.home.ui.model.CurrencyUi
import com.luongtran.cryptome.feature.home.ui.model.FilterUi
import com.luongtran.cryptome.feature.home.ui.model.HomeUiState

fun List<CurrencyInfo>.toHomeUiState(filterUi: FilterUi, numberFormatter: NumberFormatter) = when {
    isEmpty() -> HomeUiState.Empty(filterUi)
    else -> HomeUiState.Success(
        currencies = map { it.toUiModel(numberFormatter) },
        filterUi = filterUi
    )
}

private fun CurrencyInfo.toUiModel(numberFormatter: NumberFormatter): CurrencyUi = when (this) {
    is CurrencyInfo.Crypto -> CurrencyUi.Crypto(
        id = id,
        name = name,
        symbol = symbol,
        slug = slug,
        price = DisplayableNumber(
            value = priceUsd,
            formatted = numberFormatter.formatPrice(priceUsd)
        ),
        changePercent24Hr = DisplayableNumber(
            value = changePercent24Hr,
            formatted = numberFormatter.formatPercent(changePercent24Hr)
        ),
        tradable = tradable,
        iconRes = getDrawableFor(symbol)
    )

    is CurrencyInfo.Fiat -> CurrencyUi.Fiat(
        id = id,
        name = name,
        symbol = symbol,
        code = code,
        exchangeRateToUsd = DisplayableNumber(
            value = priceUsd,
            formatted = numberFormatter.formatPrice(priceUsd)
        ),
        tradable = tradable,
        iconRes = getDrawableFor(code)
    )
}