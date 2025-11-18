package com.luongtran.cryptome.feature.home.ui.mapper

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.ui.mapper.getDrawableFor
import com.luongtran.cryptome.core.ui.mapper.toDisplayableNumber
import com.luongtran.cryptome.core.ui.mapper.toPercentage
import com.luongtran.cryptome.feature.home.ui.model.CurrencyUi

fun CurrencyInfo.toUiModel(): CurrencyUi = when (this) {
    is CurrencyInfo.Crypto -> CurrencyUi.Crypto(
        id = id,
        name = name,
        symbol = symbol,
        price = priceUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toPercentage(),
        iconRes = getDrawableFor(symbol)
    )

    is CurrencyInfo.Fiat -> CurrencyUi.Fiat(
        id = id,
        name = name,
        symbol = symbol,
        code = code,
        exchangeRateToUsd = priceUsd.toDisplayableNumber(),
        iconRes = getDrawableFor(code)
    )
}