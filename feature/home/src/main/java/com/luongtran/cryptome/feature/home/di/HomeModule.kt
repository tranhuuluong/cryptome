package com.luongtran.cryptome.feature.home.di

import com.luongtran.cryptome.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
}