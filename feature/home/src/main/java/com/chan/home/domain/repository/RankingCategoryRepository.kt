package com.chan.home.domain.repository

import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryTabVO

interface RankingCategoryRepository {
    suspend fun getCategoryTabs(): List<RankingCategoryTabVO>
    suspend fun getRankingProductsByCategoryId(categoryId: String): List<ProductVO>
}