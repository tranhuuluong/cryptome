package com.luongtran.cryptome.feature.coindetail.data

import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.Result
import com.luongtran.cryptome.core.common.model.StateLoading
import com.luongtran.cryptome.core.common.model.map
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.domain.CoinDetail
import com.luongtran.cryptome.core.network.RemoteDataSource
import com.luongtran.cryptome.feature.coindetail.data.mapper.toDomainModel
import com.luongtran.cryptome.feature.coindetail.domain.CoinDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoinDetailRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val cryptoDao: CryptoCurrencyInfoDao,
) : CoinDetailRepository {

    override fun getCoinDetail(id: String): Flow<Result<CoinDetail>> = flow {
        emit(StateLoading)
        val coinInfo = cryptoDao.getById(id)
        if (coinInfo == null) {
            emit(DataStateError(Exception("Coin with id $id not found in database")))
            return@flow
        }
        emit(
            remoteDataSource.getCoinDetail(coinInfo.slug.orEmpty())
                .map { dto -> dto.toDomainModel(coinInfo) }
        )
    }
}