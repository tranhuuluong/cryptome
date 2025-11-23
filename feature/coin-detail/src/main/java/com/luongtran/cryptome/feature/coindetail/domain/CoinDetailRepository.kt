package com.luongtran.cryptome.feature.coindetail.domain

import com.luongtran.cryptome.core.common.model.Result
import com.luongtran.cryptome.core.domain.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinDetailRepository {
    fun getCoinDetail(id: String): Flow<Result<CoinDetail>>
}