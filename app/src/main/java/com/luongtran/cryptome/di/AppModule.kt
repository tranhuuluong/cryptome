package com.luongtran.cryptome.di

import com.luongtran.cryptome.core.network.AssetManager
import com.luongtran.cryptome.manager.AssetManagerImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::AssetManagerImpl).bind<AssetManager>()
    single(IoDispatcherQualifier) { Dispatchers.IO }
    single(DefaultDispatcherQualifier) { Dispatchers.Default }
}