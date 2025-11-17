package com.luongtran.cryptome.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoCurrencyInfoDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("symbol")
    val symbol: String? = null,
    @SerialName("priceUsd")
    val priceUsd: String? = null,
    @SerialName("changePercent24Hr")
    val changePercent24Hr: String? = null,
    @SerialName("marketCapUsd")
    val marketCapUsd: String? = null,
    @SerialName("tradable")
    val tradable: Boolean? = null
)