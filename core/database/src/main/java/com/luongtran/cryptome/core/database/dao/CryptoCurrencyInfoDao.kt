package com.luongtran.cryptome.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoCurrencyInfoDao {
    @Upsert
    suspend fun upsert(currencies: List<CryptoCurrencyInfoEntity>)

    @Query("SELECT * FROM ${CryptoCurrencyInfoEntity.TABLE_NAME}")
    fun getCryptoCurrencies(): Flow<List<CryptoCurrencyInfoEntity>>

    @Query("DELETE FROM ${CryptoCurrencyInfoEntity.TABLE_NAME}")
    suspend fun deleteAll()
}