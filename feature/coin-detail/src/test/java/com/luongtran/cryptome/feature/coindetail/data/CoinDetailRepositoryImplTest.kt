package com.luongtran.cryptome.feature.coindetail.data

import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.common.model.StateLoading
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.network.RemoteDataSource
import com.luongtran.cryptome.core.network.response.CoinDetailDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CoinDetailRepositoryImplTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    @MockK
    lateinit var cryptoDao: CryptoCurrencyInfoDao

    @InjectMockKs
    lateinit var repository: CoinDetailRepositoryImpl

    private val delta = 1e-9

    @Test
    fun getCoinDetail_emitsError_whenCoinMissingInDb() = runTest {
        coEvery { cryptoDao.getById("missing") } returns null

        val emissions = repository.getCoinDetail("missing").toList()

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is StateLoading)
        val error = emissions[1] as DataStateError
        assertEquals("Coin with id missing not found in database", error.exception.message)
    }

    @Test
    fun getCoinDetail_emitsSuccess_whenRemoteReturnsData() = runTest {
        val entity = CryptoCurrencyInfoEntity(
            id = "btc",
            name = "Bitcoin",
            symbol = "BTC",
            slug = "bitcoin",
            tradable = true,
            rank = 1
        )
        coEvery { cryptoDao.getById("btc") } returns entity
        val dto = CoinDetailDto(
            slug = "bitcoin",
            prices = listOf(1.0, 2.0),
            rank = 1,
            usdMarketcap = 100.0,
            usdPrice = 50000.0,
            btcVolume24h = 2000.0,
            usdPriceChange24h = 5.0
        )
        coEvery { remoteDataSource.getCoinDetail("bitcoin") } returns DataStateSuccess(dto)

        val emissions = repository.getCoinDetail("btc").toList()

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is StateLoading)
        val success = emissions[1] as DataStateSuccess
        val detail = success.data
        assertEquals("btc", detail.id)
        assertEquals("BTC", detail.symbol)
        assertEquals("Bitcoin", detail.name)
        assertEquals("bitcoin", detail.slug)
        assertEquals(listOf(1.0, 2.0), detail.priceHistory)
        assertEquals(1, detail.rank)
        assertEquals(100.0, detail.marketCapUsd, delta)
        assertEquals(50000.0, detail.priceUsd, delta)
        assertEquals(2000.0, detail.volume24Hr, delta)
        assertEquals(5.0, detail.priceChange24h, delta)
        coVerify { remoteDataSource.getCoinDetail("bitcoin") }
    }

    @Test
    fun getCoinDetail_emitsError_whenRemoteFails() = runTest {
        val entity = CryptoCurrencyInfoEntity(id = "btc", slug = "bitcoin")
        coEvery { cryptoDao.getById("btc") } returns entity
        coEvery { remoteDataSource.getCoinDetail("bitcoin") } returns DataStateError(Exception("network"))

        val emissions = repository.getCoinDetail("btc").toList()

        assertEquals(2, emissions.size)
        assertTrue(emissions[0] is StateLoading)
        val error = emissions[1] as DataStateError
        assertEquals("network", error.exception.message)
    }

    @Test
    fun getCoinDetail_usesEmptySlug_whenEntitySlugNull() = runTest {
        val entity = CryptoCurrencyInfoEntity(id = "btc", slug = null)
        coEvery { cryptoDao.getById("btc") } returns entity
        val dto = CoinDetailDto(slug = "", usdPrice = 10.0)
        coEvery { remoteDataSource.getCoinDetail("") } returns DataStateSuccess(dto)

        val emissions = repository.getCoinDetail("btc").toList()

        assertTrue(emissions[1] is DataStateSuccess)
        val detail = (emissions[1] as DataStateSuccess).data
        assertEquals("", detail.slug)
        coVerify { remoteDataSource.getCoinDetail("") }
    }
}