package com.luongtran.cryptome.feature.home.ui.mapper

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.mapper.getDrawableFor
import com.luongtran.cryptome.core.ui.mapper.toDisplayableNumber
import com.luongtran.cryptome.core.ui.mapper.toPercentage
import com.luongtran.cryptome.feature.home.ui.model.CurrencyUi
import com.luongtran.cryptome.feature.home.ui.model.FilterUi
import com.luongtran.cryptome.feature.home.ui.model.HomeUiState

fun List<CurrencyInfo>.toHomeUiState(filterUi: FilterUi) = when {
    isEmpty() -> HomeUiState.Empty
    else -> HomeUiState.Success(
        currencies = map { it.toUiModel() },
        filterUi = filterUi
    )
}

private fun CurrencyInfo.toUiModel(): CurrencyUi = when (this) {
    is CurrencyInfo.Crypto -> CurrencyUi.Crypto(
        id = id,
        name = name,
        symbol = symbol,
        price = priceUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toPercentage(),
        tradable = tradable,
        iconRes = getDrawableFor(symbol)
    )

    is CurrencyInfo.Fiat -> CurrencyUi.Fiat(
        id = id,
        name = name,
        symbol = symbol,
        code = code,
        exchangeRateToUsd = priceUsd.toDisplayableNumber(),
        tradable = tradable,
        iconRes = getDrawableFor(code)
    )
}