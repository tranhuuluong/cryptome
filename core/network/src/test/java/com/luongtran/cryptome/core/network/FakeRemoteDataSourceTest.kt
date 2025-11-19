package com.luongtran.cryptome.core.network

import com.luongtran.cryptome.core.common.manager.AssetManager
import com.luongtran.cryptome.core.common.model.DataStateError
import com.luongtran.cryptome.core.common.model.DataStateSuccess
import com.luongtran.cryptome.core.common.model.getOrNull
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.InputStream

class FakeRemoteDataSourceTest {
    @MockK
    private lateinit var assetManager: AssetManager

    @get:Rule
    val rule = MockKRule(this)

    private lateinit var dataSource: RemoteDataSource

    private lateinit var testDispatcher: CoroutineDispatcher

    @Before
    fun setUp() {
        testDispatcher = StandardTestDispatcher()
        dataSource = FakeRemoteDataSource(
            assetManager = assetManager,
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true }
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

    private fun loadResource(name: String): InputStream =
        this::class.java.getResourceAsStream("/$name") ?: error("Resource $name not found")
}