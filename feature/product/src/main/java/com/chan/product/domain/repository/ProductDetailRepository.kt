package com.chan.product.domain.repository

import com.chan.product.domain.vo.ProductDetailVO

interface ProductDetailRepository {
    suspend fun getProductDetail(productId: String): ProductDetailVO
}