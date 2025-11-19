package com.luongtran.cryptome.core.common.utils

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.math.abs
import kotlin.time.Clock

class TimeProviderImplTest {

    private lateinit var timeProvider: TimeProvider

    @Before
    fun setUp() {
        timeProvider = TimeProviderImpl()
    }

    @Test
    fun testGetCurrentTimeMillis() {
        val now = Clock.System.now()
        val providerTimeMillis = timeProvider.now()

        val delta = 100L
        val diff = abs(now.toEpochMilliseconds() - providerTimeMillis.toEpochMilliseconds())
        assertTrue(diff in 0..delta)
    }
}