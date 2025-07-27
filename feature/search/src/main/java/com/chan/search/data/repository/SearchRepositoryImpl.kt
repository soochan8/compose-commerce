package com.chan.search.data.repository

import com.chan.database.dao.ProductDao
import com.chan.domain.ProductVO
import com.chan.search.data.mappers.toDomain
import com.chan.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : SearchRepository {
    override suspend fun searchProductName(search: String): List<ProductVO> {
        return productDao.searchProductsByName(search).map { it.toDomain() }
    }
}