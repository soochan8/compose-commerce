package com.chan.product.data.repository

import com.chan.database.dao.ProductDetailDao
import com.chan.product.data.datasource.ProductDetailLocalDataSource
import com.chan.product.data.mapper.toDomain
import com.chan.product.domain.repository.ProductDetailRepository
import com.chan.product.domain.vo.ProductDetailVO
import javax.inject.Inject
import kotlinx.coroutines.delay
import java.io.IOException
import kotlin.random.Random

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

    override suspend fun downloadCoupon(couponId: String) {
        // 서버 통신 가정
        delay(2000)

        // 50% 확률 랜덤
        if (Random.nextBoolean()) {
            throw IOException("네트워크 연결이 불안정합니다. 다시 시도해주세요.")
        }
    }
}