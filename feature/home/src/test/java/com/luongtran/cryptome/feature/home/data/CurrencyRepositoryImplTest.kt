package com.luongtran.cryptome.feature.home.data

import app.cash.turbine.test
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.FiatCurrencyInfoDao
import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import com.luongtran.cryptome.core.database.util.DatabaseTransaction
import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.domain.CurrencyType
import com.luongtran.cryptome.core.network.RemoteDataSource
import com.luongtran.cryptome.core.network.response.CryptoCurrencyInfoDto
import com.luongtran.cryptome.core.network.response.FiatCurrencyInfoDto
import com.luongtran.cryptome.feature.home.data.mapper.toEntity
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyRepositoryImplTest {
    @get:Rule
    val rule = MockKRule(this)

    @RelaxedMockK
    lateinit var cryptoDao: CryptoCurrencyInfoDao

    @RelaxedMockK
    lateinit var fiatDao: FiatCurrencyInfoDao

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    @MockK
    lateinit var databaseTransaction: DatabaseTransaction

    @InjectMockKs
    lateinit var repository: CurrencyRepositoryImpl

    private val cryptoDto = CryptoCurrencyInfoDto(
        id = "bitcoin",
        name = "Bitcoin",
        symbol = "BTC",
        priceUsd = "68000.12",
        changePercent24Hr = "2.5",
        marketCapUsd = "1000000000",
        tradable = true,
        rank = 1
    )
    private val fiatDto = FiatCurrencyInfoDto(
        id = "usd",
        name = "US Dollar",
        symbol = "$",
        code = "USD",
        priceUsd = "1.0",
        tradable = true
    )

    @Before
    fun setUp() {
        coEvery { databaseTransaction.invoke<Unit>(any()) } coAnswers {
            val block = firstArg<suspend () -> Any?>()
            block()
        }
    }

    @Test
    fun insertData_fetches_and_upserts_in_transaction() = runTest {
        coEvery { remoteDataSource.getCryptoCurrencies() } returns DataStateSuccess(listOf(cryptoDto))
        coEvery { remoteDataSource.getFiatCurrencies() } returns DataStateSuccess(listOf(fiatDto))

        repository.insertData()

        val cryptoSlot = slot<List<CryptoCurrencyInfoEntity>>()
        val fiatSlot = slot<List<FiatCurrencyInfoEntity>>()

        coVerifyOrder {
            remoteDataSource.getCryptoCurrencies()
            remoteDataSource.getFiatCurrencies()
            databaseTransaction.invoke<Any>(any())
            cryptoDao.upsert(capture(cryptoSlot))
            fiatDao.upsert(capture(fiatSlot))
        }
        assertEquals(listOf(cryptoDto.toEntity()), cryptoSlot.captured)
        assertEquals(listOf(fiatDto.toEntity()), fiatSlot.captured)
    }

    @Test
    fun getCurrencies_all_crypto() = runTest {
        val entity = cryptoDto.toEntity()
        every { cryptoDao.getAll() } returns flowOf(listOf(entity))

        repository.getCurrencies(false, CurrencyType.Crypto).test {
            val items = awaitItem()
            assertEquals(1, items.size)
            assertEquals("bitcoin", (items.first() as CurrencyInfo.Crypto).id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getCurrencies_tradable_fiat_only() = runTest {
        val tradable = fiatDto.toEntity()
        every { fiatDao.getTradable() } returns flowOf(listOf(tradable))

        repository.getCurrencies(true, CurrencyType.Fiat).test {
            val items = awaitItem()
            assertEquals(1, items.size)
            assertEquals("usd", (items.first() as CurrencyInfo.Fiat).id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun clearData_deletes_in_transaction() = runTest {
        repository.clearData()
        coVerifyOrder {
            databaseTransaction.invoke<Unit>(any())
            cryptoDao.deleteAll()
            fiatDao.deleteAll()
        }
    }
}