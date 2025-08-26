package com.chan.search.domain.repository

import com.chan.domain.ProductVO
import com.chan.domain.ProductsVO
import com.chan.search.domain.model.FilterCategoriesVO
import com.chan.search.domain.model.SearchHistoryVO
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchProductName(search: String): List<ProductsVO>
    suspend fun getSearchResultProducts(search: String): List<ProductsVO>

    fun getRecentSearches(): Flow<List<SearchHistoryVO>>
    suspend fun addSearch(search: String)
    suspend fun deleteSearch(search: String)
    suspend fun clearAll()


    suspend fun getFilterCategories(): List<FilterCategoriesVO>
    suspend fun getFilteredProducts(subCategoryNames: Set<String>): List<ProductVO>
    suspend fun getFilteredProductCount(subCategoryNames: Set<String>): Int
}