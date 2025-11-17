package com.luongtran.cryptome.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FiatCurrencyInfoDao {
    @Upsert
    suspend fun upsert(currencies: List<FiatCurrencyInfoEntity>)

    @Query("SELECT * FROM ${FiatCurrencyInfoEntity.TABLE_NAME}")
    fun getFiatCurrencies(): Flow<List<FiatCurrencyInfoEntity>>

    @Query("DELETE FROM ${FiatCurrencyInfoEntity.TABLE_NAME}")
    suspend fun deleteAll()
}