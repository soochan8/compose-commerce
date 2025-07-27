package com.chan.product.data.repository

import com.chan.database.dao.ProductDetailDao
import com.chan.product.data.datasource.ProductDetailLocalDataSource
import com.chan.product.data.mapper.toDomain
import com.chan.product.domain.repository.ProductDetailRepository
import com.chan.product.domain.vo.ProductDetailVO
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val productDetailDao: ProductDetailDao,
    private val localDataSource: ProductDetailLocalDataSource
) : ProductDetailRepository {
    override suspend fun getProductDetail(productId: String): ProductDetailVO {
        var productInDb = productDetailDao.getProductDetailById(productId)

        val productFromLocal = localDataSource.getProductDetail(productId)

        if (productFromLocal != null) {
            productDetailDao.insertProductDetail(productFromLocal)

            productInDb = productFromLocal
        }
        return productInDb.toDomain()
    }
}