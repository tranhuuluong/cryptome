package com.luongtran.cryptome.core.database.di

import androidx.room.Room
import com.luongtran.cryptome.core.database.CryptomeDatabase
import com.luongtran.cryptome.core.database.dao.CryptoCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.FiatCurrencyInfoDao
import com.luongtran.cryptome.core.database.dao.RecentSearchDao
import com.luongtran.cryptome.core.database.util.DatabaseTransaction
import com.luongtran.cryptome.core.database.util.RoomDatabaseTransaction
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single<CryptomeDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = CryptomeDatabase::class.java,
            name = CryptomeDatabase.DATABASE_NAME,
        ).build()
    }
    single<CryptoCurrencyInfoDao> {
        get<CryptomeDatabase>().cryptoDao()
    }
    single<FiatCurrencyInfoDao>() {
        get<CryptomeDatabase>().fiatDao()
    }
    single<RecentSearchDao>() {
        get<CryptomeDatabase>().recentSearchDao()

    }
    singleOf(::RoomDatabaseTransaction).bind<DatabaseTransaction>()
}