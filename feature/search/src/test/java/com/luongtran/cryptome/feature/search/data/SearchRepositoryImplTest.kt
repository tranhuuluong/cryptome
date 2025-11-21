package com.luongtran.cryptome.feature.search.data

import com.luongtran.cryptome.core.common.utils.TimeProvider
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.FiatCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.RecentSearchDao
import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.RecentSearchEntity
import com.luongtran.cryptome.core.database.mapper.toDomainModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.time.Instant

class SearchRepositoryImplTest {
    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var cryptoDao: CryptoCurrencyInfoDao

    @MockK
    lateinit var fiatDao: FiatCurrencyInfoDao

    @MockK
    lateinit var recentSearchDao: RecentSearchDao

    @MockK
    lateinit var timeProvider: TimeProvider

    @InjectMockKs
    lateinit var repository: SearchRepositoryImpl

    private val cryptoEntity = CryptoCurrencyInfoEntity(
        id = "bitcoin",
        name = "Bitcoin",
        symbol = "BTC",
        priceUsd = "1000.12",
        changePercent24Hr = "2.5",
        marketCapUsd = "10000",
        tradable = true,
        rank = 1,
    )

    private val fiatEntity = FiatCurrencyInfoEntity(
        id = "usd",
        name = "US Dollar",
        symbol = "USD",
        priceUsd = "1.0",
        tradable = true,
    )

    @Test
    fun `search returns combined domain models`() = runTest {
        val cryptoDomain = cryptoEntity.toDomainModel()
        val fiatDomain = fiatEntity.toDomainModel()

        every { cryptoDao.search("btc") } returns flowOf(listOf(cryptoEntity))
        every { fiatDao.search("btc") } returns flowOf(listOf(fiatEntity))

        repository.search("btc").collect { result ->
            assertEquals(listOf(cryptoDomain, fiatDomain), result)
        }
    }

    @Test
    fun `getRecentSearches returns queries from entities`() = runTest {
        val entity = RecentSearchEntity("btc", Instant.DISTANT_PAST)
        every { recentSearchDao.getMostRecent(5) } returns flowOf(listOf(entity))

        repository.getRecentSearches(5).collect { result ->
            assertEquals(listOf("btc"), result)
        }
    }

    @Test
    fun `getPopularSearches returns domain models`() = runTest {
        val cryptoDomain = cryptoEntity.toDomainModel()
        every { cryptoDao.getPopular(3) } returns flowOf(listOf(cryptoEntity))

        repository.getPopularSearches(3).collect { result ->
            assertEquals(listOf(cryptoDomain), result)
        }
    }

    @Test
    fun `saveRecentSearch upserts entity with correct query and time`() = runTest {
        val now = Instant.parse("2024-01-01T00:00:00Z")
        every { timeProvider.now() } returns now
        coEvery { recentSearchDao.upsert(any()) } just Runs

        repository.saveRecentSearch("eth")

        coVerify {
            recentSearchDao.upsert(
                match { it.query == "eth" && it.createdAt == now }
            )
        }
    }

    @Test
    fun `clearRecentSearches calls deleteAll`() = runTest {
        coEvery { recentSearchDao.deleteAll() } just Runs

        repository.clearRecentSearches()

        coVerify { recentSearchDao.deleteAll() }
    }

}