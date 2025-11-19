package com.luongtran.cryptome.core.common.model

import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ResultExtensionsTest {
    @Test
    fun `getOrNull returns correct value for all cases`() {
        val success: Result<Int> = DataStateSuccess(42)
        val error: Result<Int> = DataStateError(Exception("fail"))
        val loading: Result<Int> = StateLoading
        val idle: Result<Int> = StateIdle

        assertEquals(42, success.getOrNull())
        assertNull(error.getOrNull())
        assertNull(loading.getOrNull())
        assertNull(idle.getOrNull())
    }

    @Test
    fun `getOrEmpty returns correct list for all cases`() {
        val success: Result<List<String>> = DataStateSuccess(listOf("a", "b"))
        val error: Result<List<String>> = DataStateError(Exception("fail"))
        val loading: Result<List<String>> = StateLoading
        val idle: Result<List<String>> = StateIdle

        assertEquals(listOf("a", "b"), success.getOrEmpty())
        assertEquals(emptyList<String>(), error.getOrEmpty())
        assertEquals(emptyList<String>(), loading.getOrEmpty())
        assertEquals(emptyList<String>(), idle.getOrEmpty())
    }

}