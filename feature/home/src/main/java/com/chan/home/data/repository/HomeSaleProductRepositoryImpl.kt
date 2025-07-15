package com.chan.home.data.repository

import com.chan.database.dao.ProductDao
import com.chan.home.data.mapper.toDomain
import com.chan.home.domain.repository.HomeSaleProductRepository
import com.chan.home.domain.vo.ProductVO
import javax.inject.Inject

class HomeSaleProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : HomeSaleProductRepository {

    override suspend fun getSaleProducts(limit: Int): List<ProductVO> {
        return productDao.getSaleProducts(limit).map { it.toDomain() }
    }
}