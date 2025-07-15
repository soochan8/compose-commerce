package com.chan.home.data.repository

import com.chan.database.dao.ProductDao
import com.chan.home.data.mapper.toDomain
import com.chan.home.domain.repository.RankingCategoryRepository
import com.chan.home.domain.vo.ProductVO
import com.chan.home.domain.vo.RankingCategoryTabVO
import javax.inject.Inject

class RankingCategoryRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : RankingCategoryRepository {
    override suspend fun getCategoryTabs(): List<RankingCategoryTabVO> {
        return productDao.getCategoryTabs().map { it.toDomain() }
    }

    override suspend fun getRankingProductsByCategoryId(categoryId: String): List<ProductVO> {
        return productDao.getProductsByCategoryId(categoryId).map { it.toDomain() }
    }
}