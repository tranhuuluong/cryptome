package com.luongtran.cryptome.core.network

import com.luongtran.cryptome.core.common.manager.AssetManager
import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.common.model.getOrNull
import com.luongtran.cryptome.core.network.testutil.loadResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RemoteDataSourceImplTest {
    @MockK
    private lateinit var assetManager: AssetManager

    @get:Rule
    val rule = MockKRule(this)

    private lateinit var dataSource: RemoteDataSource

    private lateinit var testDispatcher: CoroutineDispatcher

    private val json = Json { ignoreUnknownKeys = true }

    private fun createHttpClient(mockEngine: MockEngine): HttpClient {
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Before
    fun setUp() {
        testDispatcher = StandardTestDispatcher()
        dataSource = RemoteDataSourceImpl(
            assetManager = assetManager,
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true },
            httpClient = createHttpClient(MockEngine { respond("") })
        )
    }

    @Test
    fun `getCryptoCurrencies returns success with parsed data`() = runTest(testDispatcher) {
        every { assetManager.loadAsset(any()) } answers {
            val fileName = firstArg<String>()
            loadResource(fileName)
        }

        val result = dataSource.getCryptoCurrencies()
        assertTrue(result is DataStateSuccess)
        assertFalse(result.getOrNull().isNullOrEmpty())
    }

    @Test
    fun `getFiatCurrencies returns success with parsed data`() = runTest(testDispatcher) {
        every { assetManager.loadAsset(any()) } answers {
            val fileName = firstArg<String>()
            loadResource(fileName)
        }
        val result = dataSource.getFiatCurrencies()
        assertTrue(result is DataStateSuccess)
        assertFalse(result.getOrNull().isNullOrEmpty())
    }

    @Test
    fun `getCryptoCurrencies returns error on exception`() = runTest(testDispatcher) {
        every { assetManager.loadAsset(any()) } throws Exception("Asset not found")
        val result = dataSource.getCryptoCurrencies()
        assertTrue(result is DataStateError)
    }

    @Test
    fun `getCoinDetail returns success using btc json resource`() = runTest {
        val body = loadResource("btc.json").bufferedReader().use { it.readText() }

        val engine = MockEngine {
            respond(
                content = body,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        dataSource = createDataSource(engine)

        val result = dataSource.getCoinDetail("bitcoin") as DataStateSuccess
        val dto = result.data
        assertEquals("bitcoin", dto.slug)
        assertEquals(3, dto.prices?.size)
        assertEquals(100.0, dto.usdPrice)
        assertEquals(150.0, dto.allTime?.high)
    }

    @Test
    fun `getCoinDetail returns error on non-2xx response`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"error":"Not Found"}""",
                status = HttpStatusCode.NotFound
            )
        }

        dataSource = createDataSource(engine)

        val result = dataSource.getCoinDetail("unknown") as DataStateError
        assertEquals("Something went wrong!", result.exception.message)
    }

    private fun createDataSource(engine: HttpClientEngine): RemoteDataSource {
        return RemoteDataSourceImpl(
            assetManager = assetManager,
            ioDispatcher = testDispatcher,
            networkJson = json,
            httpClient = HttpClient(engine) {
                install(ContentNegotiation) { json(json) }
            }
        )
    }
}