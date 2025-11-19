package com.luongtran.cryptome.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.FiatCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.RecentSearchDao
import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.RecentSearchEntity
import com.luongtran.cryptome.core.database.util.InstantConverter

@Database(
    entities = [
        CryptoCurrencyInfoEntity::class,
        FiatCurrencyInfoEntity::class,
        RecentSearchEntity::class,
    ],
    version = 1
)
@TypeConverters(
    InstantConverter::class,
)
abstract class CryptomeDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoCurrencyInfoDao
    abstract fun fiatDao(): FiatCurrencyInfoDao
    abstract fun recentSearchDao(): RecentSearchDao

    companion object {
        const val DATABASE_NAME = "cryptome-database"
    }
}