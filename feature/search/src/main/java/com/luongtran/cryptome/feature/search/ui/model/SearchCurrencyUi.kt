package com.luongtran.cryptome.feature.search.ui.model

import androidx.annotation.DrawableRes
import com.luongtran.cryptome.core.ui.model.DisplayableNumber

data class SearchCurrencyUi(
    val id: String,
    val name: String,
    val code: String,
    val symbol: String,
    val priceUsd: DisplayableNumber,
    @param:DrawableRes val iconRes: Int,
)