package com.luongtran.cryptome.feature.home.data

import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.database.CryptomeDatabase
import com.luongtran.cryptome.core.database.mapper.toDomainModel
import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.network.RemoteDataSource
import com.luongtran.cryptome.feature.home.data.mapper.toEntity
import com.luongtran.cryptome.feature.home.domain.CurrencyRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CurrencyRepositoryImpl(
    database: CryptomeDatabase,
    private val remoteDataSource: RemoteDataSource,
) : CurrencyRepository {
    private val cryptoDao = database.cryptoDao()
    private val fiatDao = database.fiatDao()

    override fun getAllCurrencies(): Flow<List<CurrencyInfo>> = combine(
        cryptoDao.getCryptoCurrencies().map { entities -> entities.map { it.toDomainModel() } },
        fiatDao.getFiatCurrencies().map { entities -> entities.map { it.toDomainModel() } },
    ) { cryptoCurrencies, fiatCurrencies ->
        cryptoCurrencies + fiatCurrencies
    }

    override suspend fun insertData(): Unit = coroutineScope {
        launch {
            val response = remoteDataSource.getCryptoCurrencies()
            if (response is DataStateSuccess) {
                cryptoDao.upsert(response.data.map { it.toEntity() })
            }
        }
        launch {
            val response = remoteDataSource.getFiatCurrencies()
            if (response is DataStateSuccess) {
                fiatDao.upsert(response.data.map { it.toEntity() })
            }
        }
    }

    override suspend fun clearData() {
        cryptoDao.deleteAll()
        fiatDao.deleteAll()
    }
}