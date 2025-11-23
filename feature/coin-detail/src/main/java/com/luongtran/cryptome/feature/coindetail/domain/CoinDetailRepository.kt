package com.luongtran.cryptome.feature.coindetail.domain

import com.luongtran.cryptome.core.common.model.Result
import com.luongtran.cryptome.core.domain.CoinDetail
import com.luongtran.cryptome.core.domain.PriceHistory
import com.luongtran.cryptome.core.domain.PriceHistoryPeriod
import kotlinx.coroutines.flow.Flow

interface CoinDetailRepository {
    fun getCoinDetail(id: String): Flow<Result<CoinDetail>>
    fun getPriceHistory(
        id: String,
        period: PriceHistoryPeriod,
        convert: String = "USD"
    ): Flow<Result<PriceHistory>>
}