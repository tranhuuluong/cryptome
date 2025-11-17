package com.luongtran.cryptome.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FiatCurrencyInfoDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("symbol")
    val symbol: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("priceUsd")
    val priceUsd: String? = null
)