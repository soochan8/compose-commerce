package com.chan.search.data.repository

import com.chan.database.dao.ProductDao
import com.chan.database.dao.SearchHistoryDao
import com.chan.domain.ProductVO
import com.chan.search.data.mappers.toCategoryFilterDomain
import com.chan.search.data.mappers.toDomain
import com.chan.search.data.mappers.toSearchHistoryEntity
import com.chan.search.domain.model.FilterCategoriesVO
import com.chan.search.domain.model.SearchHistoryVO
import com.chan.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val searchHistoryDao: SearchHistoryDao
) : SearchRepository {
    override suspend fun searchProductName(search: String): List<ProductVO> {
        return productDao.searchProductsByName(search).map { it.toDomain() }
    }


    override fun getRecentSearches(): Flow<List<SearchHistoryVO>> {
        return searchHistoryDao.getRecentSearches()
            .map { entityList -> entityList.map { it.toDomain() } }
    }

    override suspend fun addSearch(search: String) {
        val addSearch = SearchHistoryVO(
            search = search,
            timeStamp = System.currentTimeMillis()
        ).toSearchHistoryEntity()
        return searchHistoryDao.insertSearch(addSearch)
    }

    override suspend fun deleteSearch(search: String) {
        return searchHistoryDao.deleteSearch(search)
    }

    override suspend fun clearAll() {
        return searchHistoryDao.clearAll()
    }

    override suspend fun getSearchResultProducts(search: String): List<ProductVO> {
        return productDao.searchProductsByName(search).map { it.toDomain() }
    }

    override suspend fun getFilterCategories(): List<FilterCategoriesVO> {
        return productDao.getFilterCategories().map { it.toCategoryFilterDomain() }
    }

    override suspend fun getFilteredProducts(subCategoryNames: Set<String>): List<ProductVO> {
        return productDao.getProductsBySubCategoryNames(subCategoryNames).map { it.toDomain() }
    }

    override suspend fun getFilteredProductCount(subCategoryNames: Set<String>): Int {
        return productDao.getProductCountBySubCategoryNames(subCategoryNames)
    }
}