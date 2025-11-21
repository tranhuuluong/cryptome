package com.luongtran.cryptome.feature.search.ui.mapper

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.mapper.getDrawableFor
import com.luongtran.cryptome.core.ui.mapper.toDisplayableNumber
import com.luongtran.cryptome.core.ui.mapper.toPercentage
import com.luongtran.cryptome.feature.search.ui.model.PopularSearchUi
import com.luongtran.cryptome.feature.search.ui.model.SearchCurrencyUi
import com.luongtran.cryptome.feature.search.ui.model.SearchUiState

fun List<CurrencyInfo>.toSearchUiState() = when {
    isEmpty() -> SearchUiState.Empty
    else -> SearchUiState.Success(
        currencies = map { it.toUiModel() }
    )
}

fun List<CurrencyInfo>.toPopularSearches() = mapNotNull { currencyInfo ->
    when (currencyInfo) {
        is CurrencyInfo.Crypto -> PopularSearchUi(
            id = currencyInfo.id,
            code = currencyInfo.symbol,
            priceUsd = currencyInfo.priceUsd.toDisplayableNumber(),
            rank = currencyInfo.rank,
            changePercent24Hr = currencyInfo.changePercent24Hr.toPercentage(),
            iconRes = getDrawableFor(currencyInfo.symbol)
        )
        else -> null
    }
}

private fun CurrencyInfo.toUiModel() = when (this) {
    is CurrencyInfo.Crypto -> SearchCurrencyUi(
        id = id,
        name = name,
        symbol = symbol,
        code = symbol,
        priceUsd = priceUsd.toDisplayableNumber(),
        iconRes = getDrawableFor(symbol)
    )

    is CurrencyInfo.Fiat -> SearchCurrencyUi(
        id = id,
        name = name,
        symbol = symbol,
        code = code,
        priceUsd = priceUsd.toDisplayableNumber(),
        iconRes = getDrawableFor(code)
    )
}