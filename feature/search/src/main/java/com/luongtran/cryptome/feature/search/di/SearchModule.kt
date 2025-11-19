package com.luongtran.cryptome.feature.search.di

import com.luongtran.cryptome.feature.search.data.SearchRepositoryImpl
import com.luongtran.cryptome.feature.search.domain.SearchRepository
import com.luongtran.cryptome.feature.search.ui.SearchViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val searchModule = module {
    viewModelOf(::SearchViewModel)
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
}