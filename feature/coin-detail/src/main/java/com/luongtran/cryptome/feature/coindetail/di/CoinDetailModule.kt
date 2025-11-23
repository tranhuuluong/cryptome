package com.luongtran.cryptome.feature.coindetail.di

import com.luongtran.cryptome.feature.coindetail.data.CoinDetailRepositoryImpl
import com.luongtran.cryptome.feature.coindetail.domain.CoinDetailRepository
import com.luongtran.cryptome.feature.coindetail.ui.CoinDetailViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coinDetailModule = module {
    viewModelOf(::CoinDetailViewModel)
    singleOf(::CoinDetailRepositoryImpl).bind<CoinDetailRepository>()
}