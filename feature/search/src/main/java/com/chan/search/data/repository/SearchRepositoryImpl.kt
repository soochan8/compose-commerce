package com.chan.search.data.repository

import com.chan.database.dao.CategoryDao
import com.chan.database.dao.ProductDao
import com.chan.database.dao.ProductsDao
import com.chan.database.dao.SearchHistoryDao
import com.chan.domain.ProductVO
import com.chan.domain.ProductsVO
import com.chan.search.data.mappers.toCategoryVO
import com.chan.search.data.mappers.toDomain
import com.chan.search.data.mappers.toProductsVO
import com.chan.search.data.mappers.toSearchHistoryEntity
import com.chan.search.domain.model.FilterCategoryListVO
import com.chan.search.domain.model.SearchHistoryVO
import com.chan.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
    private val productsDao: ProductsDao,
    private val categoryDao: CategoryDao
) : SearchRepository {
    override suspend fun searchProductName(search: String): List<ProductsVO> {
        return productsDao.searchProductsByName(search).map { it.toProductsVO() }
    }

    override suspend fun getSearchResultProducts(search: String): List<ProductsVO> {
        return productsDao.searchProductsByName(search).map { it.toProductsVO() }
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

    override suspend fun getFilterCategories(): List<FilterCategoryListVO> {
        val allCategories = categoryDao.getAllCategories()

        val childrenMap = allCategories
            .filter { it.parentCategoryId != null }
            .groupBy { it.parentCategoryId!! }

        return allCategories
            .filter { it.parentCategoryId == null }
            .map { parentEntity ->
                val childrenEntities = childrenMap[parentEntity.id] ?: emptyList()

                FilterCategoryListVO(
                    parent = parentEntity.toCategoryVO(),
                    children = childrenEntities.map { it.toCategoryVO() }
                )
            }
    }

    //DB 쿼리로 필터링 제한
    override suspend fun getFilteredProducts(selectedCategoryId: Set<String>): List<ProductsVO> {
        val allProducts = productsDao.getAllProducts()
        return allProducts
            .filter { product ->
                product.categoryIds.any { it in selectedCategoryId }
            }
            .map { it.toProductsVO() }
    }
}