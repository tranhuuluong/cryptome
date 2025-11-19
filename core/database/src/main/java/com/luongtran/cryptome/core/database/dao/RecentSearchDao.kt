package com.luongtran.cryptome.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.luongtran.cryptome.core.database.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Upsert
    suspend fun upsert(entity: RecentSearchEntity)

    @Query(
        value = """
        SELECT * FROM ${RecentSearchEntity.TABLE_NAME} 
        ORDER BY ${RecentSearchEntity.COLUMN_CREATED_AT} DESC 
        LIMIT :limit
        """
    )
    fun getMostRecent(limit: Int): Flow<List<RecentSearchEntity>>

    @Query("DELETE FROM ${RecentSearchEntity.TABLE_NAME}")
    suspend fun deleteAll()
}