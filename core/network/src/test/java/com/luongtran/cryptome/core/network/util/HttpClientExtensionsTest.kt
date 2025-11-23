package com.luongtran.cryptome.core.network.util

import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HttpClientExtensionsTest {
    @Serializable
    private data class SampleResponse(val message: String)

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `safeCall returns DataStateSuccess on 2xx response`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"message": "Hello"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(engine) {
            install(ContentNegotiation) { json(json) }
        }

        val result = safeCall<SampleResponse> { client.get("http://test.com") }

        assertTrue(result is DataStateSuccess)
        assertEquals("Hello", (result as DataStateSuccess).data.message)
    }

    @Test
    fun `safeCall returns DataStateError on non-2xx response`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"error": "Bad Request"}""",
                status = HttpStatusCode.BadRequest,
                headers = Headers.Empty
            )
        }
        val client = HttpClient(engine)

        val result = safeCall<SampleResponse> { client.get("http://test.com") }

        assertTrue(result is DataStateError)
        assertEquals("Something went wrong!", (result as DataStateError).exception.message)
    }

    @Test
    fun `safeCall catches exception and returns DataStateError`() = runTest {
        val result = safeCall<SampleResponse> {
            throw RuntimeException("Network failure")
        }
        assertTrue(result is DataStateError)
        assertEquals("Network failure", (result as DataStateError).exception.message)
    }

    @Test
    fun `safeCall rethrows CancellationException`() = runTest {
        val job = launch {
            coroutineContext.cancel()
            val dataState = safeCall<SampleResponse> {
                throw Exception("Should not be wrapped")
            }
            assertTrue(dataState is DataStateSuccess)
        }
        job.join()
    }

    @Test
    fun `toDataState maps 2xx response to DataStateSuccess`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"message": "World"}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(engine) {
            install(ContentNegotiation) { json(json) }
        }

        val response = client.get("http://test.com")
        val state = response.toDataState<SampleResponse>()

        assertTrue(state is DataStateSuccess)
        assertEquals("World", (state as DataStateSuccess).data.message)
    }

    @Test
    fun `toDataState maps non-2xx response to DataStateError`() = runTest {
        val engine = MockEngine {
            respond(
                content = """{"error":"Forbidden"}""",
                status = HttpStatusCode.Forbidden
            )
        }
        val client = HttpClient(engine)
        val response = client.get("http://test.com")

        val state = response.toDataState<SampleResponse>()

        assertTrue(state is DataStateError)
        assertEquals("Something went wrong!", (state as DataStateError).exception.message)
    }
}