package com.luongtran.cryptome.feature.home.domain

import com.luongtran.cryptome.core.domain.CurrencyInfo
import com.luongtran.cryptome.core.domain.CurrencyType
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencies(tradableOnly: Boolean, currencyType: CurrencyType): Flow<List<CurrencyInfo>>

    suspend fun insertData()

    suspend fun clearData()
}