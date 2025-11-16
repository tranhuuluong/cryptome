package com.luongtran.cryptome.feature.home.model

import androidx.annotation.DrawableRes

sealed interface CurrencyUi {
    val id: String

    data class Crypto(
        override val id: String,
        val name: String,
        val symbol: String,
        val price: DisplayableNumber,
        val changePercent24Hr: DisplayableNumber,
        @param:DrawableRes val iconRes: Int,
    ) : CurrencyUi

    data class Fiat(
        override val id: String,
        val name: String,
        val symbol: String,
        val exchangeRateToUsd: DisplayableNumber,
        @param:DrawableRes val iconRes: Int,
    ): CurrencyUi
}

data class DisplayableNumber(
    val value: Number,
    val formatted: String,
)