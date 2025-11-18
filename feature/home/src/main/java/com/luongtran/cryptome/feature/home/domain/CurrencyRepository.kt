package com.luongtran.cryptome.feature.home.domain

import com.luongtran.cryptome.core.domain.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getAllCurrencies(): Flow<List<CurrencyInfo>>

    suspend fun insertData()

    suspend fun clearData()
}