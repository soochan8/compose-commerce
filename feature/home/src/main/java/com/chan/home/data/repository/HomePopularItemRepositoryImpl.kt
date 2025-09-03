package com.chan.home.data.repository

import com.chan.database.dao.ProductsDao
import com.chan.domain.ProductsVO
import com.chan.home.data.mapper.toProductsVO
import com.chan.home.domain.repository.HomePopularItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomePopularItemRepositoryImpl @Inject constructor(
    private val productsDao: ProductsDao
) : HomePopularItemRepository {
    override fun getPopularProducts(limit: Int): Flow<List<ProductsVO>> {
        return productsDao.getPopularProducts(limit).map { list -> list.map { it.toProductsVO() } }
    }
}