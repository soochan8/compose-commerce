package com.chan.home.data.repository

import com.chan.database.dao.CategoryDao
import com.chan.database.dao.ProductsDao
import com.chan.domain.ProductsVO
import com.chan.home.data.mapper.toProductsVO
import com.chan.home.data.mapper.toRankingCategoryTabVO
import com.chan.home.domain.repository.RankingCategoryRepository
import com.chan.home.domain.vo.RankingCategoryTabVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RankingCategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val productsDao: ProductsDao,
) : RankingCategoryRepository {
    override suspend fun getCategoryTabs(): List<RankingCategoryTabVO> {
        return categoryDao.getParentCategory().map { it.toRankingCategoryTabVO() }
    }

    override fun getRankingProductsByCategoryId(categoryId: String): Flow<List<ProductsVO>> {
        return productsDao.getProductsByCategoryIdLimit(categoryId)
            .map { list -> list.map { it.toProductsVO() } }
    }
}