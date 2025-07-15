package com.chan.home.domain.repository

import com.chan.home.domain.vo.ProductVO

interface HomeSaleProductRepository {
    suspend fun getSaleProducts(limit: Int): List<ProductVO>
}