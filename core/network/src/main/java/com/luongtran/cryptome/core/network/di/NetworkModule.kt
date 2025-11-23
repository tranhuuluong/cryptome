package com.luongtran.cryptome.core.network.di

import com.luongtran.cryptome.core.common.qualifier.IoDispatcherQualifier
import com.luongtran.cryptome.core.network.RemoteDataSource
import com.luongtran.cryptome.core.network.RemoteDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(OkHttp.create()) {
            install(ContentNegotiation) {
                json(
                    get(NetworkJsonQualifier),
                )
                json(
                    get(NetworkJsonQualifier),
                    contentType = ContentType.Text.Plain
                )
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 10_000L
                socketTimeoutMillis = 10_000L
                requestTimeoutMillis = 10_000L
            }
        }
    }
    single<RemoteDataSource> {
        RemoteDataSourceImpl(
            assetManager = get(),
            ioDispatcher = get(IoDispatcherQualifier),
            networkJson = get(NetworkJsonQualifier),
            httpClient = get(),
        )
    }
    single(NetworkJsonQualifier) {
        Json {
            ignoreUnknownKeys = true
        }
    }
}