package com.luongtran.cryptome.core.database.util

import androidx.room.withTransaction
import com.luongtran.cryptome.core.database.CryptomeDatabase

interface DatabaseTransaction {
    suspend operator fun <R> invoke(block: suspend () -> R): R
}

class RoomDatabaseTransaction(
    private val database: CryptomeDatabase
) : DatabaseTransaction {
    override suspend fun <R> invoke(block: suspend () -> R): R = database.withTransaction {
        block()
    }
}