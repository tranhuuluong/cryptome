package com.luongtran.cryptome.core.common.di

import com.luongtran.cryptome.core.common.utils.TimeProvider
import com.luongtran.cryptome.core.common.utils.TimeProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    singleOf(::TimeProviderImpl).bind<TimeProvider>()
}