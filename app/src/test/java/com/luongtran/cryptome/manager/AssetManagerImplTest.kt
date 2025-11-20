package com.luongtran.cryptome.manager

import android.content.Context
import android.content.res.AssetManager
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verifySequence
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException

class AssetManagerImplTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var androidAssetManager: AssetManager

    @Test
    fun loadAsset_returns_stream_content() {
        every { context.assets } returns androidAssetManager
        every { androidAssetManager.open("test.txt") } returns ByteArrayInputStream("hello".toByteArray())

        val assetManager = AssetManagerImpl(context)
        val stream = assetManager.loadAsset("test.txt")
        val content = stream.bufferedReader().readText()
        stream.close()

        assertEquals("hello", content)
        verifySequence {
            context.assets
            androidAssetManager.open("test.txt")
        }
    }

    @Test(expected = FileNotFoundException::class)
    fun loadAsset_missing_file_throws() {
        every { context.assets } returns androidAssetManager
        every { androidAssetManager.open("missing.json") } throws FileNotFoundException("missing.json")

        val assetManager = AssetManagerImpl(context)
        assetManager.loadAsset("missing.json") // should throw
    }
}