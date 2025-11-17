package com.luongtran.cryptome.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.FiatCurrencyInfoDao
import com.luongtran.cryptome.core.database.entity.CryptoCurrencyInfoEntity
import com.luongtran.cryptome.core.database.entity.FiatCurrencyInfoEntity

@Database(
    entities = [
        CryptoCurrencyInfoEntity::class,
        FiatCurrencyInfoEntity::class,
    ],
    version = 1
)
abstract class CryptomeDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoCurrencyInfoDao
    abstract fun fiatDao(): FiatCurrencyInfoDao

    companion object {
        const val DATABASE_NAME = "cryptome-database"
    }
}