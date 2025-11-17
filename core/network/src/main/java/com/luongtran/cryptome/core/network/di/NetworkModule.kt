package com.luongtran.cryptome.core.network.di

import com.luongtran.cryptome.core.network.FakeRemoteDataSource
import com.luongtran.cryptome.core.network.RemoteDataSource
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<RemoteDataSource> {
        FakeRemoteDataSource(
            assetManager = get(),
            ioDispatcher = get(),
            networkJson = get(NetworkJsonQualifier)
        )
    }
    single(NetworkJsonQualifier) {
        Json {
            ignoreUnknownKeys = true
        }
    }
}