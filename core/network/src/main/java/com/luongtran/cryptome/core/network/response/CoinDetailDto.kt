package com.luongtran.cryptome.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailDto(
    @SerialName("slug")
    val slug: String,
    @SerialName("prices")
    val prices: List<Double>? = null,
    @SerialName("token_id")
    val tokenId: Int? = null,
    @SerialName("circulating_supply")
    val circulatingSupply: Double? = null,
    @SerialName("total_supply")
    val totalSupply: Double? = null,
    @SerialName("max_supply")
    val maxSupply: Double? = null,
    @SerialName("btc_price")
    val btcPrice: Double? = null,
    @SerialName("btc_marketcap")
    val btcMarketcap: Double? = null,
    @SerialName("btc_volume_24h")
    val btcVolume24h: Double? = null,
    @SerialName("btc_volume_30d")
    val btcVolume30d: Double? = null,
    @SerialName("btc_price_change_24h")
    val btcPriceChange24h: Double? = null,
    @SerialName("usd_price")
    val usdPrice: Double? = null,
    @SerialName("usd_marketcap")
    val usdMarketcap: Double? = null,
    @SerialName("usd_volume_24h")
    val usdVolume24h: Double? = null,
    @SerialName("usd_volume_30d")
    val usdVolume30d: Double? = null,
    @SerialName("usd_price_change_24h")
    val usdPriceChange24h: Double? = null,
    @SerialName("usd_price_change_24h_abs")
    val usdPriceChange24hAbs: Double? = null,
    @SerialName("token_dominance_rate")
    val tokenDominanceRate: Double? = null,
    @SerialName("rank")
    val rank: Int? = null,
    @SerialName("app_tradable")
    val appTradable: Boolean? = null,
    @SerialName("exchange_tradable")
    val exchangeTradable: Boolean? = null,
    @SerialName("defi_tradable")
    val defiTradable: Boolean? = null,
    @SerialName("update_time")
    val updateTime: Long? = null,
    @SerialName("price_update_time")
    val priceUpdateTime: Long? = null,
    @SerialName("app_symbol")
    val appSymbol: String? = null,
    @SerialName("all_time")
    val allTime: AllTimeStatsDto? = null,
    @SerialName("visibility")
    val visibility: String? = null
)

@Serializable
data class AllTimeStatsDto(
    @SerialName("high")
    val high: Double? = null,
    @SerialName("high_timestamp")
    val highTimestamp: Long? = null,
    @SerialName("low")
    val low: Double? = null,
    @SerialName("low_timestamp")
    val lowTimestamp: Long? = null
)