package com.luongtran.cryptome

import android.app.Application
import com.luongtran.cryptome.core.database.di.databaseModule
import com.luongtran.cryptome.core.network.di.networkModule
import com.luongtran.cryptome.di.appModule
import com.luongtran.cryptome.feature.home.di.homeModule
import com.luongtran.cryptome.feature.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CryptomeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CryptomeApplication)
            modules(appModule, homeModule, searchModule, networkModule, databaseModule)
        }
    }
}