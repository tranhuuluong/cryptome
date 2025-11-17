package com.luongtran.cryptome.core.database.di

import androidx.room.Room
import com.luongtran.cryptome.core.database.CryptomeDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<CryptomeDatabase> {
        Room.databaseBuilder(
            context = get(),
            klass = CryptomeDatabase::class.java,
            name = CryptomeDatabase.DATABASE_NAME,
        ).build()
    }
}