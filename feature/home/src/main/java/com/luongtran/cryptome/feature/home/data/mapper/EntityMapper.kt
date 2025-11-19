package com.luongtran.cryptome.feature.home.data.mapper

import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import com.luongtran.cryptome.core.network.response.CryptoCurrencyInfoDto
import com.luongtran.cryptome.core.network.response.FiatCurrencyInfoDto

fun CryptoCurrencyInfoDto.toEntity() = CryptoCurrencyInfoEntity(
    id = id,
    name = name,
    symbol = symbol,
    priceUsd = priceUsd,
    changePercent24Hr = changePercent24Hr,
    marketCapUsd = marketCapUsd,
    tradable = tradable,
    rank = rank,
)

fun FiatCurrencyInfoDto.toEntity() = FiatCurrencyInfoEntity(
    id = id,
    name = name,
    symbol = symbol,
    code = code,
    priceUsd = priceUsd,
    tradable = tradable,
)
