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
    fun getAll(): Flow<List<FiatCurrencyInfoEntity>>

    @Query(
        """
        SELECT * FROM ${FiatCurrencyInfoEntity.TABLE_NAME}
        WHERE ${FiatCurrencyInfoEntity.COLUMN_TRADABLE} = 1
        """
    )
    fun getTradable(): Flow<List<FiatCurrencyInfoEntity>>

    @Query(
        """
        SELECT * FROM ${FiatCurrencyInfoEntity.TABLE_NAME}
        WHERE ${FiatCurrencyInfoEntity.COLUMN_NAME} LIKE :query || '%'
        OR ${FiatCurrencyInfoEntity.COLUMN_NAME} LIKE '% ' || :query || '%'
        OR ${FiatCurrencyInfoEntity.COLUMN_CODE} LIKE :query || '%'
    """
    )
    fun search(query: String): Flow<List<FiatCurrencyInfoEntity>>

    @Query("DELETE FROM ${FiatCurrencyInfoEntity.TABLE_NAME}")
    suspend fun deleteAll()
}