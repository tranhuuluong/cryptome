package com.luongtran.cryptome.core.database.mapper

import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import com.luongtran.cryptome.core.domain.CurrencyInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class CurrencyInfoMapperTest {

    @Test
    fun `crypto entity maps to domain model correctly`() {
        val entity = CryptoCurrencyInfoEntity(
            id = "btc",
            name = "Bitcoin",
            symbol = "BTC",
            priceUsd = "50000.5",
            changePercent24Hr = "2.5",
            marketCapUsd = "1000000000",
            tradable = true
        )
        val domain = entity.toDomainModel()
        assertEquals(
            CurrencyInfo.Crypto(
                id = "btc",
                name = "Bitcoin",
                symbol = "BTC",
                priceUsd = BigDecimal("50000.5"),
                changePercent24Hr = BigDecimal("2.5"),
                marketCapUsd = BigDecimal("1000000000"),
                tradable = true
            ), domain
        )
    }

    @Test
    fun `fiat entity maps to domain model correctly`() {
        val entity = FiatCurrencyInfoEntity(
            id = "usd",
            name = "US Dollar",
            symbol = "$",
            code = "USD",
            priceUsd = "1.0",
            tradable = false
        )
        val domain = entity.toDomainModel()
        assertEquals(
            CurrencyInfo.Fiat(
                id = "usd",
                name = "US Dollar",
                symbol = "$",
                code = "USD",
                priceUsd = BigDecimal("1.0"),
                tradable = false
            ), domain
        )
    }

    @Test
    fun `crypto entity handles nulls and defaults`() {
        val entity = CryptoCurrencyInfoEntity(
            id = "eth",
            name = null,
            symbol = null,
            priceUsd = null,
            changePercent24Hr = null,
            marketCapUsd = null,
            tradable = null
        )
        val domain = entity.toDomainModel()
        assertEquals(
            CurrencyInfo.Crypto(
                id = "eth",
                name = "",
                symbol = "",
                priceUsd = BigDecimal.ZERO,
                changePercent24Hr = BigDecimal.ZERO,
                marketCapUsd = BigDecimal.ZERO,
                tradable = false
            ), domain
        )
    }

    @Test
    fun `fiat entity handles nulls and defaults`() {
        val entity = FiatCurrencyInfoEntity(
            id = "eur",
            name = null,
            symbol = null,
            code = null,
            priceUsd = null,
            tradable = null
        )
        val domain = entity.toDomainModel()
        assertEquals(
            CurrencyInfo.Fiat(
                id = "eur",
                name = "",
                symbol = "",
                code = "",
                priceUsd = BigDecimal.ZERO,
                tradable = false
            ), domain
        )
    }
}