package com.luongtran.cryptome.feature.search.ui.model

import androidx.annotation.DrawableRes
import com.luongtran.cryptome.core.ui.model.DisplayableNumber

data class PopularSearchUi(
    val id: String,
    val code: String,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val rank: Int,
    @param:DrawableRes val iconRes: Int,
)