package com.luongtran.cryptome.feature.search.di

import com.luongtran.cryptome.feature.search.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchModule = module {
    viewModelOf(::SearchViewModel)
}