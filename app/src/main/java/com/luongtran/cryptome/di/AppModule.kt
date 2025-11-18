package com.luongtran.cryptome.di

import com.luongtran.cryptome.core.common.manager.AssetManager
import com.luongtran.cryptome.core.common.qualifier.DefaultDispatcherQualifier
import com.luongtran.cryptome.core.common.qualifier.IoDispatcherQualifier
import com.luongtran.cryptome.manager.AssetManagerImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::AssetManagerImpl).bind<AssetManager>()
    single<CoroutineDispatcher>(IoDispatcherQualifier) { Dispatchers.IO }
    single<CoroutineDispatcher>(DefaultDispatcherQualifier) { Dispatchers.Default }
}