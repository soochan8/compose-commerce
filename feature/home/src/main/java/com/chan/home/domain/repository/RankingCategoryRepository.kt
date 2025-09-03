package com.chan.home.domain.repository

import com.chan.domain.ProductsVO
import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryTabVO
import kotlinx.coroutines.flow.Flow

interface RankingCategoryRepository {
    suspend fun getCategoryTabs(): List<RankingCategoryTabVO>
    fun getRankingProductsByCategoryId(categoryId: String): Flow<List<ProductsVO>>
}