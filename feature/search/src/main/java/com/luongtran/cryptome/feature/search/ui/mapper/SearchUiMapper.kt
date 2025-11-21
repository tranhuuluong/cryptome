package com.luongtran.cryptome.feature.search.ui.mapper

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.mapper.getDrawableFor
import com.luongtran.cryptome.core.ui.model.DisplayableNumber
import com.luongtran.cryptome.core.ui.utils.NumberFormatter
import com.luongtran.cryptome.feature.search.ui.model.PopularSearchUi
import com.luongtran.cryptome.feature.search.ui.model.SearchCurrencyUi
import com.luongtran.cryptome.feature.search.ui.model.SearchUiState

fun List<CurrencyInfo>.toSearchUiState(
    query: String,
    numberFormatter: NumberFormatter,
) = when {
    isEmpty() -> SearchUiState.Empty(query)
    else -> SearchUiState.Success(
        currencies = map { it.toUiModel(numberFormatter) }
    )
}

fun List<CurrencyInfo>.toPopularSearches(
    numberFormatter: NumberFormatter
) = mapNotNull { currencyInfo ->
    when (currencyInfo) {
        is CurrencyInfo.Crypto -> currencyInfo.toPopularSearchUi(numberFormatter)
        else -> null
    }
}

private fun CurrencyInfo.Crypto.toPopularSearchUi(
    numberFormatter: NumberFormatter
) = PopularSearchUi(
    id = id,
    code = symbol,
    priceUsd = DisplayableNumber(
        value = priceUsd,
        formatted = numberFormatter.formatPrice(priceUsd)
    ),
    rank = rank,
    changePercent24Hr = DisplayableNumber(
        value = changePercent24Hr,
        formatted = numberFormatter.formatPercent(changePercent24Hr)
    ),
    iconRes = getDrawableFor(symbol)
)

private fun CurrencyInfo.toUiModel(numberFormatter: NumberFormatter) = when (this) {
    is CurrencyInfo.Crypto -> SearchCurrencyUi(
        id = id,
        name = name,
        symbol = symbol,
        code = symbol,
        priceUsd = DisplayableNumber(
            value = priceUsd,
            formatted = numberFormatter.formatPrice(priceUsd)
        ),
        iconRes = getDrawableFor(symbol)
    )

    is CurrencyInfo.Fiat -> SearchCurrencyUi(
        id = id,
        name = name,
        symbol = symbol,
        code = code,
        priceUsd = DisplayableNumber(
            value = priceUsd,
            formatted = numberFormatter.formatPrice(priceUsd)
        ),
        iconRes = getDrawableFor(code)
    )
}