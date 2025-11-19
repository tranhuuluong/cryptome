package com.luongtran.cryptome.core.database.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import kotlin.time.Instant

class InstantConverterTest {

    private val converter = InstantConverter()

    @Test
    fun `longToInstant converts correctly`() {
        val millis = 1000L
        val instant = converter.longToInstant(millis)
        assertEquals(Instant.fromEpochMilliseconds(millis), instant)
    }

    @Test
    fun `longToInstant returns null for null input`() {
        assertNull(converter.longToInstant(null))
    }

    @Test
    fun `instantToLong converts correctly`() {
        val instant = Instant.fromEpochMilliseconds(1000L)
        val millis = converter.instantToLong(instant)
        assertEquals(1000L, millis)
    }

    @Test
    fun `instantToLong returns null for null input`() {
        assertNull(converter.instantToLong(null))
    }
}