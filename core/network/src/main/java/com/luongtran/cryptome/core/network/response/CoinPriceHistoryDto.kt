package com.luongtran.cryptome.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceHistoryDto(
    @SerialName("price_change")
    val priceChange: Double? = null,

    @SerialName("usd_price_change")
    val usdPriceChange: Double? = null,

    @SerialName("prices")
    val prices: List<List<Double?>>? = null
)