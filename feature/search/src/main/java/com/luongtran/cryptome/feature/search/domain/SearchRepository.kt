package com.luongtran.cryptome.feature.search.domain

import com.luongtran.cryptome.core.domain.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<List<CurrencyInfo>>

    fun getRecentSearches(limit: Int): Flow<List<String>>

    suspend fun saveRecentSearch(query: String)

    suspend fun clearRecentSearches()
}