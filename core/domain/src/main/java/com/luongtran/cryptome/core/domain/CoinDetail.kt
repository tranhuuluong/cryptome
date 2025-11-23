package com.luongtran.cryptome.core.domain

data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    val slug: String,
    val priceHistory: List<Double>,
    val rank: Int,
    val priceUsd: Double,
    val marketCapUsd: Double,
    val volume24Hr: Double,
    val priceChange24h: Double,
    val allTimeHigh: Double,
    val allTimeLow: Double,
    val totalSupply: Double,
    val maxSupply: Double,
    val circulatingSupply: Double,
)