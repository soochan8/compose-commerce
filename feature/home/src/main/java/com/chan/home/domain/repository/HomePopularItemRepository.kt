package com.chan.home.domain.repository

import com.chan.domain.ProductsVO
import kotlinx.coroutines.flow.Flow

interface HomePopularItemRepository {
    fun getPopularProducts(limit: Int): Flow<List<ProductsVO>>
}