package com.luongtran.cryptome.feature.search.ui.mapper

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.mapper.getDrawableFor
import com.luongtran.cryptome.core.ui.mapper.toDisplayableNumber
import com.luongtran.cryptome.feature.search.ui.model.SearchCurrencyUi
import com.luongtran.cryptome.feature.search.ui.model.SearchUiState

fun List<CurrencyInfo>.toSearchUiState() = when {
    isEmpty() -> SearchUiState.Empty
    else -> SearchUiState.Success(
        currencies = map { it.toUiModel() }
    )
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