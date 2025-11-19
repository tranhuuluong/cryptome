package com.luongtran.cryptome.feature.search.data

import com.luongtran.cryptome.core.common.utils.TimeProvider
import com.luongtran.cryptome.core.common.utils.mapItems
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.FiatCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.RecentSearchDao
import com.luongtran.cryptome.core.database.entity.RecentSearchEntity
import com.luongtran.cryptome.core.database.mapper.toDomainModel
import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.feature.search.domain.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SearchRepositoryImpl(
    private val cryptoDao: CryptoCurrencyInfoDao,
    private val fiatDao: FiatCurrencyInfoDao,
    private val recentSearchDao: RecentSearchDao,
    private val timeProvider: TimeProvider,
) : SearchRepository {
    override fun search(query: String): Flow<List<CurrencyInfo>> = combine(
        cryptoDao.search(query),
        fiatDao.search(query)
    ) { cryptos, fiats ->
        cryptos.map { entity -> entity.toDomainModel() } +
                fiats.map { entity -> entity.toDomainModel() }
    }

    override fun getRecentSearches(limit: Int): Flow<List<String>> =
        recentSearchDao.getMostRecent(limit).mapItems { entity -> entity.query }

    override suspend fun saveRecentSearch(query: String) {
        recentSearchDao.upsert(
            RecentSearchEntity(
                query = query,
                createdAt = timeProvider.now(),
            )
        )
    }

    override suspend fun clearRecentSearches() {
        recentSearchDao.deleteAll()
    }
}