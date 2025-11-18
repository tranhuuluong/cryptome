package com.luongtran.cryptome.core.database.mapper

import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import com.luongtran.cryptome.core.domain.CurrencyInfo
import java.math.BigDecimal

fun CryptoCurrencyInfoEntity.toDomainModel() = CurrencyInfo.Crypto(
    id = id,
    name = name.orEmpty(),
    symbol = symbol.orEmpty(),
    priceUsd = priceUsd.toBigDecimalOrZero(),
    changePercent24Hr = changePercent24Hr.toBigDecimalOrZero(),
    marketCapUsd = marketCapUsd.toBigDecimalOrZero(),
    tradable = tradable ?: false,
)

fun FiatCurrencyInfoEntity.toDomainModel() = CurrencyInfo.Fiat(
    id = id,
    name = name.orEmpty(),
    symbol = symbol.orEmpty(),
    code = code.orEmpty(),
    priceUsd = priceUsd.toBigDecimalOrZero(),
    tradable = tradable ?: false,
)

private fun String?.toBigDecimalOrZero() = this?.toBigDecimalOrNull() ?: BigDecimal.ZERO