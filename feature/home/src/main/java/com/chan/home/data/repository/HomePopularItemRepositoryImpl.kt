package com.chan.home.data.repository

import com.chan.database.dao.ProductDao
import com.chan.home.data.mapper.toDomain
import com.chan.home.domain.repository.HomePopularItemRepository
import com.chan.home.domain.vo.ProductVO
import javax.inject.Inject
import kotlin.collections.map

class HomePopularItemRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : HomePopularItemRepository {
    override suspend fun getPopularProducts(limit: Int): List<ProductVO> {
        return productDao.getPopularProducts(limit).map { it.toDomain() }
    }
}