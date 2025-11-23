package com.luongtran.cryptome.feature.coindetail.data.mapper

import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.domain.CoinDetail
import com.luongtran.cryptome.core.domain.PriceHistory
import com.luongtran.cryptome.core.domain.PriceHistoryPeriod
import com.luongtran.cryptome.core.network.response.CoinDetailDto
import com.luongtran.cryptome.core.network.response.CoinPriceHistoryDto

fun CoinDetailDto.toDomainModel(
    coinInfo: CryptoCurrencyInfoEntity,
) = CoinDetail(
    id = coinInfo.id,
    symbol = coinInfo.symbol.orEmpty(),
    name = coinInfo.name.orEmpty(),
    slug = slug,
    priceHistory = prices.orEmpty(),
    rank = rank ?: Int.MIN_VALUE,
    marketCapUsd = usdMarketcap ?: Double.NaN,
    priceUsd = usdPrice ?: Double.NaN,
    volume24Hr = btcVolume24h ?: Double.NaN,
    priceChange24h = usdPriceChange24h ?: Double.NaN,
    allTimeHigh = allTime?.high ?: Double.NaN,
    allTimeLow = allTime?.low ?: Double.NaN,
    totalSupply = totalSupply ?: Double.NaN,
    maxSupply = maxSupply ?: Double.NaN,
    circulatingSupply = circulatingSupply ?: Double.NaN,
)

fun CoinPriceHistoryDto.toDomainModel(
    period: PriceHistoryPeriod,
) = PriceHistory(
    prices = prices?.map { list -> list.getOrNull(1) ?: Double.NaN }.orEmpty(),
    period = period,
    changePercent = usdPriceChange ?: Double.NaN
)