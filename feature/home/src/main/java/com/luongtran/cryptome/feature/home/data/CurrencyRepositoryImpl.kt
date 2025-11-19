package com.luongtran.cryptome.feature.home.data

import com.luongtran.cryptome.core.common.model.getOrEmpty
import com.luongtran.cryptome.core.common.utils.mapItems
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.FiatCurrencyInfoDao
import com.luongtran.cryptome.core.database.mapper.toDomainModel
import com.luongtran.cryptome.core.database.util.DatabaseTransaction
import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.domain.CurrencyType
import com.luongtran.cryptome.core.network.RemoteDataSource
import com.luongtran.cryptome.feature.home.data.mapper.toEntity
import com.luongtran.cryptome.feature.home.domain.CurrencyRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class CurrencyRepositoryImpl(
    private val cryptoDao: CryptoCurrencyInfoDao,
    private val fiatDao: FiatCurrencyInfoDao,
    private val databaseTransaction: DatabaseTransaction,
    private val remoteDataSource: RemoteDataSource,
) : CurrencyRepository {

    override fun getCurrencies(
        tradableOnly: Boolean,
        currencyType: CurrencyType
    ): Flow<List<CurrencyInfo>> {
        val cryptoFlow = (if (tradableOnly) cryptoDao.getTradable() else cryptoDao.getAll())
            .mapItems { entity -> entity.toDomainModel() }
        val fiatFlow = (if (tradableOnly) fiatDao.getTradable() else fiatDao.getAll())
            .mapItems { entity -> entity.toDomainModel() }
        return when (currencyType) {
            CurrencyType.Crypto -> cryptoFlow
            CurrencyType.Fiat -> fiatFlow
        }
    }

    override suspend fun insertData(): Unit = coroutineScope {
        val cryptoDeferred = async {
            remoteDataSource.getCryptoCurrencies()
                .getOrEmpty()
                .map { dto -> dto.toEntity() }
        }
        val fiatDeferred = async {
            remoteDataSource.getFiatCurrencies()
                .getOrEmpty()
                .map { dto -> dto.toEntity() }
        }
        val cryptos = cryptoDeferred.await()
        val fiats = fiatDeferred.await()
        databaseTransaction {
            cryptoDao.upsert(cryptos)
            fiatDao.upsert(fiats)
        }
    }

    override suspend fun clearData() = databaseTransaction {
        cryptoDao.deleteAll()
        fiatDao.deleteAll()
    }
}