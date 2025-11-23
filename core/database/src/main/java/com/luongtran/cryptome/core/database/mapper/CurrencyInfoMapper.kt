package com.luongtran.cryptome.core.database.mapper

import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.domain.mapper.toDoubleOrNaN

fun CryptoCurrencyInfoEntity.toDomainModel() = CurrencyInfo.Crypto(
    id = id,
    name = name.orEmpty(),
    symbol = symbol.orEmpty(),
    slug = slug.orEmpty(),
    priceUsd = priceUsd.toDoubleOrNaN(),
    changePercent24Hr = changePercent24Hr.toDoubleOrNaN(),
    marketCapUsd = marketCapUsd.toDoubleOrNaN(),
    tradable = tradable ?: false,
    rank = rank ?: Int.MIN_VALUE,
)

fun FiatCurrencyInfoEntity.toDomainModel() = CurrencyInfo.Fiat(
    id = id,
    name = name.orEmpty(),
    symbol = symbol.orEmpty(),
    code = code.orEmpty(),
    priceUsd = priceUsd.toDoubleOrNaN(),
    tradable = tradable ?: false,
)