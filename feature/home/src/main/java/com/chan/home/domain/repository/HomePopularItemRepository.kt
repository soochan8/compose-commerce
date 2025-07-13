package com.chan.home.domain.repository

import com.chan.home.domain.vo.ProductVO

interface HomePopularItemRepository {
    suspend fun getPopularProducts(limit: Int): List<ProductVO>
}