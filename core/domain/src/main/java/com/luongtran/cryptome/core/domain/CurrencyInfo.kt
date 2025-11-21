package com.luongtran.cryptome.core.domain

import java.math.BigDecimal

sealed interface CurrencyInfo {
    data class Crypto(
        val id: String,
        val name: String,
        val symbol: String,
        val priceUsd: BigDecimal,
        val changePercent24Hr: BigDecimal,
        val marketCapUsd: BigDecimal,
        val tradable: Boolean,
        val rank: Int,
    ) : CurrencyInfo

    data class Fiat(
        val id: String,
        val name: String,
        val symbol: String,
        val code: String,
        val priceUsd: BigDecimal,
        val tradable: Boolean,
    ) : CurrencyInfo
}