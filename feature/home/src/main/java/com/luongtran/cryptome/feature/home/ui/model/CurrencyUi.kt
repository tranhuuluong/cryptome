package com.luongtran.cryptome.feature.home.ui.model

import androidx.annotation.DrawableRes
import com.luongtran.cryptome.core.ui.model.DisplayableNumber

sealed interface CurrencyUi {
    val id: String

    data class Crypto(
        override val id: String,
        val name: String,
        val symbol: String,
        val slug: String,
        val price: DisplayableNumber,
        val changePercent24Hr: DisplayableNumber,
        val tradable: Boolean,
        @param:DrawableRes val iconRes: Int,
    ) : CurrencyUi

    data class Fiat(
        override val id: String,
        val name: String,
        val symbol: String,
        val code: String,
        val exchangeRateToUsd: DisplayableNumber,
        val tradable: Boolean,
        @param:DrawableRes val iconRes: Int,
    ) : CurrencyUi
}