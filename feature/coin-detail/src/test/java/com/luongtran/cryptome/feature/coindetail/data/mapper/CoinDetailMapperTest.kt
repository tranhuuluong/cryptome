package com.luongtran.cryptome.feature.coindetail.data.mapper

import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.network.response.AllTimeStatsDto
import com.luongtran.cryptome.core.network.response.CoinDetailDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CoinDetailMapperTest {
    private val delta = 1e-9

    @Test
    fun mapDtoToDomain_allFieldsMapped() {
        val entity = CryptoCurrencyInfoEntity(
            id = "btc",
            name = "Bitcoin",
            symbol = "BTC",
            slug = "bitcoin",
            tradable = true,
            rank = 1
        )
        val dto = CoinDetailDto(
            slug = "bitcoin",
            prices = listOf(1.0, 2.0),
            rank = 1,
            usdMarketcap = 100.0,
            usdPrice = 50000.0,
            btcVolume24h = 2000.0,
            usdPriceChange24h = 5.0,
            allTime = AllTimeStatsDto(high = 60000.0, low = 100.0),
            totalSupply = 18000000.0,
            maxSupply = 21000000.0,
            circulatingSupply = 17500000.0
        )

        val result = dto.toDomainModel(entity)

        assertEquals("btc", result.id)
        assertEquals("BTC", result.symbol)
        assertEquals("Bitcoin", result.name)
        assertEquals("bitcoin", result.slug)
        assertEquals(listOf(1.0, 2.0), result.priceHistory)
        assertEquals(1, result.rank)
        assertEquals(100.0, result.marketCapUsd, delta)
        assertEquals(50000.0, result.priceUsd, delta)
        assertEquals(2000.0, result.volume24Hr, delta)
        assertEquals(5.0, result.priceChange24h, delta)
        assertEquals(60000.0, result.allTimeHigh, delta)
        assertEquals(100.0, result.allTimeLow, delta)
        assertEquals(18000000.0, result.totalSupply, delta)
        assertEquals(21000000.0, result.maxSupply, delta)
        assertEquals(17500000.0, result.circulatingSupply, delta)
    }

    @Test
    fun mapDtoToDomain_nullFields_useFallbacks() {
        val entity = CryptoCurrencyInfoEntity(id = "btc")
        val dto = CoinDetailDto(slug = "bitcoin")

        val result = dto.toDomainModel(entity)

        assertEquals("btc", result.id)
        assertEquals("", result.symbol)
        assertEquals("", result.name)
        assertEquals("bitcoin", result.slug)
        assertTrue(result.priceHistory.isEmpty())
        assertEquals(Int.MIN_VALUE, result.rank)
        assertTrue(result.marketCapUsd.isNaN())
        assertTrue(result.priceUsd.isNaN())
        assertTrue(result.volume24Hr.isNaN())
        assertTrue(result.priceChange24h.isNaN())
        assertTrue(result.allTimeHigh.isNaN())
        assertTrue(result.allTimeLow.isNaN())
        assertTrue(result.totalSupply.isNaN())
        assertTrue(result.maxSupply.isNaN())
        assertTrue(result.circulatingSupply.isNaN())
    }

    @Test
    fun mapDtoToDomain_volumePrefersBtcVolume24h() {
        val entity =
            CryptoCurrencyInfoEntity(id = "btc", name = "Bitcoin", symbol = "BTC", slug = "bitcoin")
        val dto = CoinDetailDto(
            slug = "bitcoin",
            btcVolume24h = 1234.0,
            usdVolume24h = 9999.0
        )

        val result = dto.toDomainModel(entity)

        assertEquals(1234.0, result.volume24Hr, delta)
    }
}