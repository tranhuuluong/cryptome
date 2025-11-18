package com.luongtran.cryptome.feature.home.di

import com.luongtran.cryptome.feature.home.data.CurrencyRepositoryImpl
import com.luongtran.cryptome.feature.home.domain.CurrencyRepository
import com.luongtran.cryptome.feature.home.ui.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    singleOf(::CurrencyRepositoryImpl).bind<CurrencyRepository>()
}