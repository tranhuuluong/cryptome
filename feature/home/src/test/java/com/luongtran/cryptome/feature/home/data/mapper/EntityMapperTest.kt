package com.luongtran.cryptome.feature.home.data.mapper

import com.luongtran.cryptome.core.network.response.CryptoCurrencyInfoDto
import com.luongtran.cryptome.core.network.response.FiatCurrencyInfoDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class EntityMapperTest {
    @Test
    fun `crypto dto toEntity maps all fields`() {
        val dto = CryptoCurrencyInfoDto(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            priceUsd = "68000.12",
            changePercent24Hr = "2.56",
            marketCapUsd = "1234567890.99",
            tradable = true,
            rank = 1
        )

        val entity = dto.toEntity()

        assertEquals(dto.id, entity.id)
        assertEquals(dto.name, entity.name)
        assertEquals(dto.symbol, entity.symbol)
        assertEquals(dto.priceUsd, entity.priceUsd)
        assertEquals(dto.changePercent24Hr, entity.changePercent24Hr)
        assertEquals(dto.marketCapUsd, entity.marketCapUsd)
        assertEquals(dto.tradable, entity.tradable)
        assertEquals(dto.rank, entity.rank)
    }

    @Test
    fun `crypto dto toEntity preserves nulls`() {
        val dto = CryptoCurrencyInfoDto(
            id = "eth",
            name = null,
            symbol = null,
            priceUsd = null,
            changePercent24Hr = null,
            marketCapUsd = null,
            tradable = null,
            rank = null
        )

        val entity = dto.toEntity()

        assertEquals(dto.id, entity.id)
        assertNull(entity.name)
        assertNull(entity.symbol)
        assertNull(entity.priceUsd)
        assertNull(entity.changePercent24Hr)
        assertNull(entity.marketCapUsd)
        assertNull(entity.tradable)
        assertNull(entity.rank)
    }

    @Test
    fun `fiat dto toEntity maps all fields`() {
        val dto = FiatCurrencyInfoDto(
            id = "usd",
            name = "United States Dollar",
            symbol = "$",
            code = "USD",
            priceUsd = "1.00",
            tradable = true
        )

        val entity = dto.toEntity()

        assertEquals(dto.id, entity.id)
        assertEquals(dto.name, entity.name)
        assertEquals(dto.symbol, entity.symbol)
        assertEquals(dto.code, entity.code)
        assertEquals(dto.priceUsd, entity.priceUsd)
        assertEquals(dto.tradable, entity.tradable)
    }

    @Test
    fun `fiat dto toEntity preserves nulls`() {
        val dto = FiatCurrencyInfoDto(
            id = "eur",
            name = null,
            symbol = null,
            code = null,
            priceUsd = null,
            tradable = null
        )

        val entity = dto.toEntity()

        assertEquals(dto.id, entity.id)
        assertNull(entity.name)
        assertNull(entity.symbol)
        assertNull(entity.code)
        assertNull(entity.priceUsd)
        assertNull(entity.tradable)
    }

}