package com.luongtran.cryptome.core.database.util

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.luongtran.cryptome.core.database.CryptomeDatabase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RoomDatabaseTransactionTest {

    @Test
    fun `invoke executes block and returns result`() = runTest {
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CryptomeDatabase::class.java
        ).build()

        val trans = RoomDatabaseTransaction(db)
        val result = trans { "result" }

        assertEquals("result", result)
        db.close()
    }
}