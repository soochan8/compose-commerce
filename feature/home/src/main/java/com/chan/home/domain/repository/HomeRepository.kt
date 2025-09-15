package com.chan.home.domain.repository

import com.chan.domain.ProductsVO
import com.chan.home.domain.vo.HomeBannerVO
import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryTabVO
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getBanners(): List<HomeBannerVO>
    fun getPopularProducts(limit: Int): Flow<List<ProductsVO>>
    suspend fun getSaleProducts(limit: Int): List<ProductVO>
    suspend fun getCategoryTabs(): List<RankingCategoryTabVO>
    fun getRankingProductsByCategoryId(categoryId: String): Flow<List<ProductsVO>>
}