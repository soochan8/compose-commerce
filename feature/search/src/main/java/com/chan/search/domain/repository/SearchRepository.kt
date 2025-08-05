package com.chan.search.domain.repository

import com.chan.domain.ProductVO
import com.chan.search.domain.model.SearchHistoryVO
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchProductName(search: String): List<ProductVO>

    fun getRecentSearches(): Flow<List<SearchHistoryVO>>
    suspend fun addSearch(search: String)
    suspend fun deleteSearch(search: String)
    suspend fun clearAll()

    suspend fun getSearchResultProducts(search: String): List<ProductVO>
}