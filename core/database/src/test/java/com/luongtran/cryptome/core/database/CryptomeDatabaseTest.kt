package com.luongtran.cryptome.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.FiatCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.RecentSearchDao
import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.RecentSearchEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Instant


@RunWith(RobolectricTestRunner::class)
class CryptomeDatabaseTest {
    private lateinit var db: CryptomeDatabase
    private lateinit var cryptoDao: CryptoCurrencyInfoDao
    private lateinit var fiatDao: FiatCurrencyInfoDao
    private lateinit var recentSearchDao: RecentSearchDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CryptomeDatabase::class.java
        ).build()
        cryptoDao = db.cryptoDao()
        fiatDao = db.fiatDao()
        recentSearchDao = db.recentSearchDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `cryptoCurrencyInfoDao full CRUD and queries`() = runTest {
        val entity1 = CryptoCurrencyInfoEntity(
            id = "btc",
            name = "Bitcoin",
            symbol = "BTC",
            priceUsd = "50000",
            changePercent24Hr = "2.5",
            marketCapUsd = "1000000000",
            tradable = true,
            rank = 1
        )
        val entity2 = CryptoCurrencyInfoEntity(
            id = "eth",
            name = "Ethereum",
            symbol = "ETH",
            priceUsd = "4000",
            changePercent24Hr = "1.2",
            marketCapUsd = "500000000",
            tradable = false,
            rank = 2
        )

        cryptoDao.upsert(listOf(entity1, entity2))

        val all = cryptoDao.getAll().first()
        assertEquals(2, all.size)
        assertTrue(all.any { it.id == "btc" })
        assertTrue(all.any { it.id == "eth" })

        val tradable = cryptoDao.getTradable().first()
        assertEquals(1, tradable.size)
        assertEquals("btc", tradable[0].id)

        val searchByName = cryptoDao.search("Bit").first()
        assertEquals(1, searchByName.size)
        assertEquals("btc", searchByName[0].id)

        val popular = cryptoDao.getPopular(1).first()
        assertEquals(1, popular.size)
        assertEquals("btc", popular[0].id)

        cryptoDao.deleteAll()
        assertTrue(cryptoDao.getAll().first().isEmpty())
    }

    @Test
    fun `fiatCurrencyInfoDao full CRUD and queries`() = runTest {
        val entity1 = FiatCurrencyInfoEntity(
            id = "usd",
            name = "US Dollar",
            symbol = "$",
            code = "USD",
            priceUsd = "1.0",
            tradable = true
        )
        val entity2 = FiatCurrencyInfoEntity(
            id = "eur",
            name = "Euro",
            symbol = "€",
            code = "EUR",
            priceUsd = "1.2",
            tradable = false
        )

        fiatDao.upsert(listOf(entity1, entity2))

        val all = fiatDao.getAll().first()
        assertEquals(2, all.size)
        assertTrue(all.any { it.id == "usd" })
        assertTrue(all.any { it.id == "eur" })

        val tradable = fiatDao.getTradable().first()
        assertEquals(1, tradable.size)
        assertEquals("usd", tradable[0].id)

        val searchByCode = fiatDao.search("EU").first()
        assertEquals(1, searchByCode.size)
        assertEquals("eur", searchByCode[0].id)

        fiatDao.deleteAll()
        assertTrue(fiatDao.getAll().first().isEmpty())
    }

    @Test
    fun `recentSearchDao full CRUD and queries`() = runTest {
        val now = Instant.fromEpochMilliseconds(1000L)
        val entity1 = RecentSearchEntity(query = "bitcoin", createdAt = now)
        val entity2 = RecentSearchEntity(
            query = "ethereum",
            createdAt = now.plus(1000.milliseconds)
        )

        recentSearchDao.upsert(entity1)
        recentSearchDao.upsert(entity2)

        val mostRecent = recentSearchDao.getMostRecent(1).first()
        assertEquals(1, mostRecent.size)
        assertEquals("ethereum", mostRecent[0].query)

        recentSearchDao.deleteAll()
        assertTrue(recentSearchDao.getMostRecent(10).first().isEmpty())
    }
}