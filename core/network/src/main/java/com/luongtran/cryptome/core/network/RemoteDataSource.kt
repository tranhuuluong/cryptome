package com.luongtran.cryptome.core.network

import com.luongtran.cryptome.core.common.manager.AssetManager
import com.luongtran.cryptome.core.common.model.DataState
import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.network.response.CryptoCurrencyInfoDto
import com.luongtran.cryptome.core.network.response.CoinDetailDto
import com.luongtran.cryptome.core.network.response.FiatCurrencyInfoDto
import com.luongtran.cryptome.core.network.util.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

interface RemoteDataSource {
    suspend fun getCryptoCurrencies(): DataState<List<CryptoCurrencyInfoDto>>
    suspend fun getFiatCurrencies(): DataState<List<FiatCurrencyInfoDto>>
    suspend fun getCoinDetail(slug: String): DataState<CoinDetailDto>
}

@OptIn(ExperimentalSerializationApi::class)
class RemoteDataSourceImpl(
    private val assetManager: AssetManager,
    private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val httpClient: HttpClient,
) : RemoteDataSource {
    override suspend fun getCryptoCurrencies(): DataState<List<CryptoCurrencyInfoDto>> =
        loadDataFromAsset<List<CryptoCurrencyInfoDto>>(CRYPTO_ASSET)

    override suspend fun getFiatCurrencies(): DataState<List<FiatCurrencyInfoDto>> =
        loadDataFromAsset<List<FiatCurrencyInfoDto>>(FIAT_ASSET)

    override suspend fun getCoinDetail(slug: String): DataState<CoinDetailDto> {
        return safeCall<CoinDetailDto> {
            val url = "$BASE_URL/price/v1/token-price/$slug"
            httpClient.get(url)
        }
    }

    private suspend inline fun <reified T> loadDataFromAsset(
        fileName: String
    ): DataState<T> = withContext(ioDispatcher) {
        try {
            val data = assetManager.loadAsset(fileName).use { inputStream ->
                networkJson.decodeFromStream<T>(inputStream)
            }
            DataStateSuccess(data)
        } catch (e: Exception) {
            ensureActive() // re-throw CancellationException
            DataStateError(e)
        }
    }

    companion object {
        private const val CRYPTO_ASSET = "crypto.json"
        private const val FIAT_ASSET = "fiat.json"
        private const val BASE_URL = "https://price-api.crypto.com"
    }
}