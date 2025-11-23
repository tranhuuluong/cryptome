package com.luongtran.cryptome.core.domain

sealed interface CurrencyInfo {
    data class Crypto(
        val id: String,
        val name: String,
        val symbol: String,
        val slug: String,
        val priceUsd: Double,
        val changePercent24Hr: Double,
        val marketCapUsd: Double,
        val tradable: Boolean,
        val rank: Int,
    ) : CurrencyInfo

    data class Fiat(
        val id: String,
        val name: String,
        val symbol: String,
        val code: String,
        val priceUsd: Double,
        val tradable: Boolean,
    ) : CurrencyInfo
}