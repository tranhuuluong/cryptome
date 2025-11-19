package com.luongtran.cryptome.core.common.utils

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FlowExtensionsTest {
    @Test
    fun `mapItems transforms all elements in emitted lists`() = runTest {
        val inputFlow = flowOf(listOf(1, 2, 3))
        val result = inputFlow.mapItems { it * 2 }.toList()
        assertEquals(listOf(listOf(2, 4, 6)), result)
    }
}