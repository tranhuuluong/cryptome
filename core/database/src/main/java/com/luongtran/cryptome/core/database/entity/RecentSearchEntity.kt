package com.luongtran.cryptome.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.time.Instant


@Entity(
    tableName = RecentSearchEntity.TABLE_NAME,
    indices = [
        Index(value = [RecentSearchEntity.COLUMN_CREATED_AT])
    ]
)
data class RecentSearchEntity(
    @ColumnInfo(name = COLUMN_QUERY)
    @PrimaryKey
    val query: String,
    @ColumnInfo(name = COLUMN_CREATED_AT)
    val createdAt: Instant,
) {
    companion object {
        const val TABLE_NAME = "recent_searches"
        const val COLUMN_QUERY = "query"
        const val COLUMN_CREATED_AT = "created_at"
    }
}
